package com.org.callguard.screening;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002\u00a8\u0006\f"}, d2 = {"Lcom/org/callguard/screening/CallGuardScreeningService;", "Landroid/telecom/CallScreeningService;", "()V", "onScreenCall", "", "callDetails", "Landroid/telecom/Call$Details;", "postBlockedCallNotification", "result", "Lcom/org/callguard/domain/DecisionResult;", "respond", "Companion", "app_debug"})
public final class CallGuardScreeningService extends android.telecom.CallScreeningService {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CallGuardScreening";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.screening.CallGuardScreeningService.Companion Companion = null;
    
    public CallGuardScreeningService() {
        super();
    }
    
    @java.lang.Override()
    public void onScreenCall(@org.jetbrains.annotations.NotNull()
    android.telecom.Call.Details callDetails) {
    }
    
    private final void respond(android.telecom.Call.Details callDetails, com.org.callguard.domain.DecisionResult result) {
    }
    
    private final void postBlockedCallNotification(com.org.callguard.domain.DecisionResult result) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/org/callguard/screening/CallGuardScreeningService$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}