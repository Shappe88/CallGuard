package com.org.callguard.domain;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/org/callguard/domain/AuditLogger;", "", "()V", "TAG", "", "log", "", "db", "Lcom/org/callguard/data/db/CallGuardDatabase;", "direction", "result", "Lcom/org/callguard/domain/DecisionResult;", "currentBlocklistVersion", "", "(Lcom/org/callguard/data/db/CallGuardDatabase;Ljava/lang/String;Lcom/org/callguard/domain/DecisionResult;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AuditLogger {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuditLogger";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.domain.AuditLogger INSTANCE = null;
    
    private AuditLogger() {
        super();
    }
    
    /**
     * Logs a call decision. This must never throw or block the call path —
     * any failure here is swallowed and only logged to logcat.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object log(@org.jetbrains.annotations.NotNull()
    com.org.callguard.data.db.CallGuardDatabase db, @org.jetbrains.annotations.NotNull()
    java.lang.String direction, @org.jetbrains.annotations.NotNull()
    com.org.callguard.domain.DecisionResult result, long currentBlocklistVersion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}