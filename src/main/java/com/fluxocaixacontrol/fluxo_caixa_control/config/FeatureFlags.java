package com.fluxocaixacontrol.fluxo_caixa_control.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "feature")
@Data
public class FeatureFlags {
    private boolean awsSqsEnabled = false;       // false agora, true no AWS
    private boolean legacyParallelEnabled = false; // true durante migração
    private boolean batchSchedulingEnabled = true;
}