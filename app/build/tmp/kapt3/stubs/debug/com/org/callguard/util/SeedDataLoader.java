package com.org.callguard.util;

/**
 * Loads the embedded seed_blocklist.json (bundled in app assets) into the
 * local database on first run. This guarantees the app has a usable
 * blocklist/allowlist immediately after install, before any network sync.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/org/callguard/util/SeedDataLoader;", "", "()V", "SEED_ASSET", "", "TAG", "loadSeedData", "", "context", "Landroid/content/Context;", "db", "Lcom/org/callguard/data/db/CallGuardDatabase;", "(Landroid/content/Context;Lcom/org/callguard/data/db/CallGuardDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SeedDataLoader {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SeedDataLoader";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String SEED_ASSET = "seed_blocklist.json";
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.util.SeedDataLoader INSTANCE = null;
    
    private SeedDataLoader() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadSeedData(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.org.callguard.data.db.CallGuardDatabase db, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}