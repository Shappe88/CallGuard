package com.org.callguard.redirection;

/**
 * Evaluates OUTGOING calls before they are placed.
 *
 * CallRedirectionService callbacks (placeCallUnmodified / redirectCall /
 * cancelCall) must be called synchronously from onPlaceCall — there is no
 * officially documented async pattern, and the OS enforces a short timeout.
 * To keep this reliable, we run the (fast, local-only) decision with a
 * blocking-with-timeout wrapper. If anything is slow or fails, we place the
 * call unmodified (fail-safe ALLOW).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\f"}, d2 = {"Lcom/org/callguard/redirection/CallGuardRedirectionService;", "Landroid/telecom/CallRedirectionService;", "()V", "onPlaceCall", "", "handle", "Landroid/net/Uri;", "initialPhoneAccount", "Landroid/telecom/PhoneAccountHandle;", "allowInteractiveResponse", "", "Companion", "app_debug"})
public final class CallGuardRedirectionService extends android.telecom.CallRedirectionService {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CallGuardRedirection";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.redirection.CallGuardRedirectionService.Companion Companion = null;
    
    public CallGuardRedirectionService() {
        super();
    }
    
    @java.lang.Override()
    public void onPlaceCall(@org.jetbrains.annotations.NotNull()
    android.net.Uri handle, @org.jetbrains.annotations.NotNull()
    android.telecom.PhoneAccountHandle initialPhoneAccount, boolean allowInteractiveResponse) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/org/callguard/redirection/CallGuardRedirectionService$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}