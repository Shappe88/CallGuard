package com.org.callguard.domain

enum class CallDecision {
    ALLOW,
    BLOCK
}

enum class MatchedRule {
    EMERGENCY,
    INTERNAL_ALLOWLIST,
    SOC_HELPDESK,
    TEMPORARY_OVERRIDE,
    BLOCKLIST_MATCH,
    KILL_SWITCH_ACTIVE,
    NO_MATCH_ALLOW,
    INVALID_NUMBER,
    FAILSAFE_ERROR
}

data class DecisionResult(
    val decision: CallDecision,
    val matchedRule: MatchedRule,
    val normalizedNumber: String?
)
