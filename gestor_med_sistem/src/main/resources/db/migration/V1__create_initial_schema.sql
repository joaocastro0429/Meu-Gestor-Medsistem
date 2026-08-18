CREATE TABLE saas_plan (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    default_price NUMERIC(15,2) NOT NULL CHECK (default_price >= 0),
    duration_days INTEGER NOT NULL CHECK (duration_days > 0),
    status VARCHAR(20) NOT NULL
);

CREATE TABLE saas_module (
    id UUID PRIMARY KEY,
    code VARCHAR(60) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE company (
    id UUID PRIMARY KEY,
    legal_name VARCHAR(180) NOT NULL,
    trade_name VARCHAR(180) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE saas_plan_module (
    plan_id UUID NOT NULL REFERENCES saas_plan(id) ON DELETE CASCADE,
    module_id UUID NOT NULL REFERENCES saas_module(id) ON DELETE CASCADE,
    PRIMARY KEY (plan_id, module_id)
);

CREATE TABLE company_subscription (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    plan_id UUID NOT NULL REFERENCES saas_plan(id),
    price NUMERIC(15,2) NOT NULL CHECK (price >= 0),
    starts_on DATE NOT NULL,
    expires_on DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CHECK (expires_on >= starts_on)
);

CREATE TABLE company_module (
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    module_id UUID NOT NULL REFERENCES saas_module(id) ON DELETE CASCADE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (company_id, module_id)
);

CREATE TABLE branch (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    code VARCHAR(60) NOT NULL,
    name VARCHAR(120) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (company_id, code)
);

CREATE TABLE company_settings (
    company_id UUID PRIMARY KEY REFERENCES company(id) ON DELETE CASCADE,
    timezone VARCHAR(60) NOT NULL,
    locale VARCHAR(20) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    settings JSONB NOT NULL DEFAULT '{}'::jsonb
);

CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE user_permission (
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    module_id UUID NOT NULL REFERENCES saas_module(id) ON DELETE CASCADE,
    can_view BOOLEAN NOT NULL DEFAULT FALSE,
    can_create BOOLEAN NOT NULL DEFAULT FALSE,
    can_edit BOOLEAN NOT NULL DEFAULT FALSE,
    can_delete BOOLEAN NOT NULL DEFAULT FALSE,
    can_approve BOOLEAN NOT NULL DEFAULT FALSE,
    can_manage BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (user_id, module_id)
);

CREATE TABLE user_branch (
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    branch_id UUID NOT NULL REFERENCES branch(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, branch_id)
);

CREATE TABLE password_reset_token (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    used_at TIMESTAMPTZ
);

CREATE INDEX idx_subscription_company ON company_subscription(company_id);
CREATE INDEX idx_branch_company ON branch(company_id);
CREATE INDEX idx_user_company ON app_user(company_id);
CREATE INDEX idx_reset_token_user ON password_reset_token(user_id);
