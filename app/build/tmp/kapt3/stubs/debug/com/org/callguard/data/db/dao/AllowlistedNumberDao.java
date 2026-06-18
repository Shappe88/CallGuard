package com.org.callguard.data.db.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/org/callguard/data/db/dao/AllowlistedNumberDao;", "", "deleteNonOverrides", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findActive", "Lcom/org/callguard/data/db/entity/AllowlistedNumberEntity;", "number", "", "now", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "", "insert", "entry", "(Lcom/org/callguard/data/db/entity/AllowlistedNumberEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertAll", "entries", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "purgeExpiredOverrides", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface AllowlistedNumberDao {
    
    /**
     * Returns an active, non-expired allowlist match (any category), if present.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM allowlisted_numbers\n        WHERE normalizedNumber = :number\n          AND active = 1\n          AND (expiresAt IS NULL OR expiresAt > :now)\n        LIMIT 1\n        ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findActive(@org.jetbrains.annotations.NotNull()
    java.lang.String number, long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.org.callguard.data.db.entity.AllowlistedNumberEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.org.callguard.data.db.entity.AllowlistedNumberEntity> entries, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.org.callguard.data.db.entity.AllowlistedNumberEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM allowlisted_numbers WHERE category != \'OVERRIDE\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteNonOverrides(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM allowlisted_numbers WHERE category = \'OVERRIDE\' AND expiresAt <= :now")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object purgeExpiredOverrides(long now, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM allowlisted_numbers")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.org.callguard.data.db.entity.AllowlistedNumberEntity>> $completion);
}