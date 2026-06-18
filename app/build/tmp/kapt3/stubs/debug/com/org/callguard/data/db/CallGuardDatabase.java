package com.org.callguard.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&\u00a8\u0006\u0012"}, d2 = {"Lcom/org/callguard/data/db/CallGuardDatabase;", "Landroidx/room/RoomDatabase;", "()V", "adminMessageDao", "Lcom/org/callguard/data/db/dao/AdminMessageDao;", "allowlistedNumberDao", "Lcom/org/callguard/data/db/dao/AllowlistedNumberDao;", "auditEventDao", "Lcom/org/callguard/data/db/dao/AuditEventDao;", "blockedNumberDao", "Lcom/org/callguard/data/db/dao/BlockedNumberDao;", "blocklistVersionDao", "Lcom/org/callguard/data/db/dao/BlocklistVersionDao;", "policyMetadataDao", "Lcom/org/callguard/data/db/dao/PolicyMetadataDao;", "syncStatusDao", "Lcom/org/callguard/data/db/dao/SyncStatusDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.org.callguard.data.db.entity.BlockedNumberEntity.class, com.org.callguard.data.db.entity.AllowlistedNumberEntity.class, com.org.callguard.data.db.entity.BlocklistVersionEntity.class, com.org.callguard.data.db.entity.SyncStatusEntity.class, com.org.callguard.data.db.entity.PolicyMetadataEntity.class, com.org.callguard.data.db.entity.AuditEventEntity.class, com.org.callguard.data.db.entity.AdminMessageEntity.class}, version = 1, exportSchema = true)
public abstract class CallGuardDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.org.callguard.data.db.CallGuardDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.org.callguard.data.db.CallGuardDatabase.Companion Companion = null;
    
    public CallGuardDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.BlockedNumberDao blockedNumberDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.AllowlistedNumberDao allowlistedNumberDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.BlocklistVersionDao blocklistVersionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.SyncStatusDao syncStatusDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.PolicyMetadataDao policyMetadataDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.AuditEventDao auditEventDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.org.callguard.data.db.dao.AdminMessageDao adminMessageDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/org/callguard/data/db/CallGuardDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/org/callguard/data/db/CallGuardDatabase;", "build", "context", "Landroid/content/Context;", "getInstance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.org.callguard.data.db.CallGuardDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private final com.org.callguard.data.db.CallGuardDatabase build(android.content.Context context) {
            return null;
        }
    }
}