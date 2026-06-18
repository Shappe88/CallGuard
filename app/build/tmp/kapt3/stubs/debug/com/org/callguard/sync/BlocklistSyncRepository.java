package com.org.callguard.sync;

/**
 * Coordinates OTA blocklist sync:
 * - Checks server version vs local version.
 * - Tries delta first, falls back to full refresh.
 * - Verifies signature + checksum BEFORE applying anything.
 * - Applies atomically (single Room transaction); on any failure mid-apply,
 *   the transaction rolls back and the previous version remains active.
 * - Updates sync_status and blocklist_versions for audit/rollback tracking.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0013J&\u0010\u0014\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0018J&\u0010\u0019\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0018J.\u0010\u001b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010 J0\u0010!\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010#J&\u0010$\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/org/callguard/sync/BlocklistSyncRepository;", "", "context", "Landroid/content/Context;", "db", "Lcom/org/callguard/data/db/CallGuardDatabase;", "api", "Lcom/org/callguard/sync/BlocklistApi;", "(Landroid/content/Context;Lcom/org/callguard/data/db/CallGuardDatabase;Lcom/org/callguard/sync/BlocklistApi;)V", "applyDelta", "", "delta", "Lcom/org/callguard/data/model/BlocklistDeltaPayload;", "now", "", "(Lcom/org/callguard/data/model/BlocklistDeltaPayload;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "applyFullRefresh", "payload", "Lcom/org/callguard/data/model/BlocklistPayload;", "(Lcom/org/callguard/data/model/BlocklistPayload;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markSyncAttempt", "currentVersion", "deviceId", "", "(JJLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markSyncSuccess", "newVersion", "recordError", "error", "(JJLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sync", "Lcom/org/callguard/sync/SyncResult;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryDelta", "remoteVersion", "(JJJLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryFullRefresh", "Companion", "app_debug"})
public final class BlocklistSyncRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.org.callguard.data.db.CallGuardDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.org.callguard.sync.BlocklistApi api = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "BlocklistSync";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.sync.BlocklistSyncRepository.Companion Companion = null;
    
    public BlocklistSyncRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.org.callguard.data.db.CallGuardDatabase db, @org.jetbrains.annotations.NotNull()
    com.org.callguard.sync.BlocklistApi api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.org.callguard.sync.SyncResult> $completion) {
        return null;
    }
    
    private final java.lang.Object tryDelta(long currentVersion, long remoteVersion, long now, java.lang.String deviceId, kotlin.coroutines.Continuation<? super com.org.callguard.sync.SyncResult> $completion) {
        return null;
    }
    
    private final java.lang.Object applyDelta(com.org.callguard.data.model.BlocklistDeltaPayload delta, long now, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object tryFullRefresh(long currentVersion, long now, java.lang.String deviceId, kotlin.coroutines.Continuation<? super com.org.callguard.sync.SyncResult> $completion) {
        return null;
    }
    
    /**
     * Atomic swap: stage new data, then replace old data only after the new
     * set is fully written. If any step throws, the previously applied
     * version remains marked APPLIED and continues to be used.
     */
    private final java.lang.Object applyFullRefresh(com.org.callguard.data.model.BlocklistPayload payload, long now, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object markSyncAttempt(long now, long currentVersion, java.lang.String deviceId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object markSyncSuccess(long now, long newVersion, java.lang.String deviceId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object recordError(long now, long currentVersion, java.lang.String deviceId, java.lang.String error, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/org/callguard/sync/BlocklistSyncRepository$Companion;", "", "()V", "TAG", "", "getCanonicalBytes", "", "payload", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final byte[] getCanonicalBytes(java.lang.Object payload) {
            return null;
        }
    }
}