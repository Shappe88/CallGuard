package com.org.callguard.data.db.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\rH\u00a7@\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/org/callguard/data/db/dao/BlocklistVersionDao;", "", "getAll", "", "Lcom/org/callguard/data/db/entity/BlocklistVersionEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentApplied", "insert", "", "entry", "(Lcom/org/callguard/data/db/entity/BlocklistVersionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateStatus", "version", "", "status", "", "appliedAt", "(JLjava/lang/String;Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface BlocklistVersionDao {
    
    @androidx.room.Query(value = "SELECT * FROM blocklist_versions WHERE status = \'APPLIED\' ORDER BY version DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCurrentApplied(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.org.callguard.data.db.entity.BlocklistVersionEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.org.callguard.data.db.entity.BlocklistVersionEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE blocklist_versions SET status = :status, appliedAt = :appliedAt WHERE version = :version")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStatus(long version, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.Nullable()
    java.lang.Long appliedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM blocklist_versions ORDER BY version DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.org.callguard.data.db.entity.BlocklistVersionEntity>> $completion);
}