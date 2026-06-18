package com.org.callguard.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.org.callguard.data.db.dao.AdminMessageDao;
import com.org.callguard.data.db.dao.AdminMessageDao_Impl;
import com.org.callguard.data.db.dao.AllowlistedNumberDao;
import com.org.callguard.data.db.dao.AllowlistedNumberDao_Impl;
import com.org.callguard.data.db.dao.AuditEventDao;
import com.org.callguard.data.db.dao.AuditEventDao_Impl;
import com.org.callguard.data.db.dao.BlockedNumberDao;
import com.org.callguard.data.db.dao.BlockedNumberDao_Impl;
import com.org.callguard.data.db.dao.BlocklistVersionDao;
import com.org.callguard.data.db.dao.BlocklistVersionDao_Impl;
import com.org.callguard.data.db.dao.PolicyMetadataDao;
import com.org.callguard.data.db.dao.PolicyMetadataDao_Impl;
import com.org.callguard.data.db.dao.SyncStatusDao;
import com.org.callguard.data.db.dao.SyncStatusDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CallGuardDatabase_Impl extends CallGuardDatabase {
  private volatile BlockedNumberDao _blockedNumberDao;

  private volatile AllowlistedNumberDao _allowlistedNumberDao;

  private volatile BlocklistVersionDao _blocklistVersionDao;

  private volatile SyncStatusDao _syncStatusDao;

  private volatile PolicyMetadataDao _policyMetadataDao;

  private volatile AuditEventDao _auditEventDao;

  private volatile AdminMessageDao _adminMessageDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `blocked_numbers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `normalizedNumber` TEXT NOT NULL, `displayLabel` TEXT, `reasonCode` TEXT NOT NULL, `severity` TEXT NOT NULL, `source` TEXT NOT NULL, `version` INTEGER NOT NULL, `signatureStatus` TEXT NOT NULL, `active` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `expiresAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `allowlisted_numbers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `normalizedNumber` TEXT NOT NULL, `displayLabel` TEXT, `category` TEXT NOT NULL, `source` TEXT NOT NULL, `approvedBy` TEXT, `version` INTEGER NOT NULL, `active` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `expiresAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `blocklist_versions` (`version` INTEGER NOT NULL, `releasedAt` INTEGER NOT NULL, `signature` TEXT NOT NULL, `checksum` TEXT NOT NULL, `appliedAt` INTEGER, `status` TEXT NOT NULL, PRIMARY KEY(`version`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sync_status` (`id` INTEGER NOT NULL, `lastSyncAttempt` INTEGER, `lastSuccessfulSync` INTEGER, `currentVersion` INTEGER NOT NULL, `pendingVersion` INTEGER, `lastError` TEXT, `deviceId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `policy_metadata` (`key` TEXT NOT NULL, `value` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, `signature` TEXT, PRIMARY KEY(`key`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `audit_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `direction` TEXT NOT NULL, `normalizedNumber` TEXT NOT NULL, `decision` TEXT NOT NULL, `matchedRule` TEXT NOT NULL, `blocklistVersion` INTEGER NOT NULL, `uploaded` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `admin_messages` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `body` TEXT NOT NULL, `severity` TEXT NOT NULL, `issuedAt` INTEGER NOT NULL, `expiresAt` INTEGER, `acknowledged` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '81f54286426f75ab8bf28865c9a87c05')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `blocked_numbers`");
        db.execSQL("DROP TABLE IF EXISTS `allowlisted_numbers`");
        db.execSQL("DROP TABLE IF EXISTS `blocklist_versions`");
        db.execSQL("DROP TABLE IF EXISTS `sync_status`");
        db.execSQL("DROP TABLE IF EXISTS `policy_metadata`");
        db.execSQL("DROP TABLE IF EXISTS `audit_events`");
        db.execSQL("DROP TABLE IF EXISTS `admin_messages`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBlockedNumbers = new HashMap<String, TableInfo.Column>(12);
        _columnsBlockedNumbers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("normalizedNumber", new TableInfo.Column("normalizedNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("displayLabel", new TableInfo.Column("displayLabel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("reasonCode", new TableInfo.Column("reasonCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("signatureStatus", new TableInfo.Column("signatureStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("active", new TableInfo.Column("active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedNumbers.put("expiresAt", new TableInfo.Column("expiresAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlockedNumbers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlockedNumbers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlockedNumbers = new TableInfo("blocked_numbers", _columnsBlockedNumbers, _foreignKeysBlockedNumbers, _indicesBlockedNumbers);
        final TableInfo _existingBlockedNumbers = TableInfo.read(db, "blocked_numbers");
        if (!_infoBlockedNumbers.equals(_existingBlockedNumbers)) {
          return new RoomOpenHelper.ValidationResult(false, "blocked_numbers(com.org.callguard.data.db.entity.BlockedNumberEntity).\n"
                  + " Expected:\n" + _infoBlockedNumbers + "\n"
                  + " Found:\n" + _existingBlockedNumbers);
        }
        final HashMap<String, TableInfo.Column> _columnsAllowlistedNumbers = new HashMap<String, TableInfo.Column>(11);
        _columnsAllowlistedNumbers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("normalizedNumber", new TableInfo.Column("normalizedNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("displayLabel", new TableInfo.Column("displayLabel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("approvedBy", new TableInfo.Column("approvedBy", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("version", new TableInfo.Column("version", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("active", new TableInfo.Column("active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAllowlistedNumbers.put("expiresAt", new TableInfo.Column("expiresAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAllowlistedNumbers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAllowlistedNumbers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAllowlistedNumbers = new TableInfo("allowlisted_numbers", _columnsAllowlistedNumbers, _foreignKeysAllowlistedNumbers, _indicesAllowlistedNumbers);
        final TableInfo _existingAllowlistedNumbers = TableInfo.read(db, "allowlisted_numbers");
        if (!_infoAllowlistedNumbers.equals(_existingAllowlistedNumbers)) {
          return new RoomOpenHelper.ValidationResult(false, "allowlisted_numbers(com.org.callguard.data.db.entity.AllowlistedNumberEntity).\n"
                  + " Expected:\n" + _infoAllowlistedNumbers + "\n"
                  + " Found:\n" + _existingAllowlistedNumbers);
        }
        final HashMap<String, TableInfo.Column> _columnsBlocklistVersions = new HashMap<String, TableInfo.Column>(6);
        _columnsBlocklistVersions.put("version", new TableInfo.Column("version", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklistVersions.put("releasedAt", new TableInfo.Column("releasedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklistVersions.put("signature", new TableInfo.Column("signature", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklistVersions.put("checksum", new TableInfo.Column("checksum", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklistVersions.put("appliedAt", new TableInfo.Column("appliedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklistVersions.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlocklistVersions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlocklistVersions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlocklistVersions = new TableInfo("blocklist_versions", _columnsBlocklistVersions, _foreignKeysBlocklistVersions, _indicesBlocklistVersions);
        final TableInfo _existingBlocklistVersions = TableInfo.read(db, "blocklist_versions");
        if (!_infoBlocklistVersions.equals(_existingBlocklistVersions)) {
          return new RoomOpenHelper.ValidationResult(false, "blocklist_versions(com.org.callguard.data.db.entity.BlocklistVersionEntity).\n"
                  + " Expected:\n" + _infoBlocklistVersions + "\n"
                  + " Found:\n" + _existingBlocklistVersions);
        }
        final HashMap<String, TableInfo.Column> _columnsSyncStatus = new HashMap<String, TableInfo.Column>(7);
        _columnsSyncStatus.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("lastSyncAttempt", new TableInfo.Column("lastSyncAttempt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("lastSuccessfulSync", new TableInfo.Column("lastSuccessfulSync", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("currentVersion", new TableInfo.Column("currentVersion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("pendingVersion", new TableInfo.Column("pendingVersion", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("lastError", new TableInfo.Column("lastError", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSyncStatus.put("deviceId", new TableInfo.Column("deviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSyncStatus = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSyncStatus = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSyncStatus = new TableInfo("sync_status", _columnsSyncStatus, _foreignKeysSyncStatus, _indicesSyncStatus);
        final TableInfo _existingSyncStatus = TableInfo.read(db, "sync_status");
        if (!_infoSyncStatus.equals(_existingSyncStatus)) {
          return new RoomOpenHelper.ValidationResult(false, "sync_status(com.org.callguard.data.db.entity.SyncStatusEntity).\n"
                  + " Expected:\n" + _infoSyncStatus + "\n"
                  + " Found:\n" + _existingSyncStatus);
        }
        final HashMap<String, TableInfo.Column> _columnsPolicyMetadata = new HashMap<String, TableInfo.Column>(4);
        _columnsPolicyMetadata.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicyMetadata.put("value", new TableInfo.Column("value", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicyMetadata.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPolicyMetadata.put("signature", new TableInfo.Column("signature", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPolicyMetadata = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPolicyMetadata = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPolicyMetadata = new TableInfo("policy_metadata", _columnsPolicyMetadata, _foreignKeysPolicyMetadata, _indicesPolicyMetadata);
        final TableInfo _existingPolicyMetadata = TableInfo.read(db, "policy_metadata");
        if (!_infoPolicyMetadata.equals(_existingPolicyMetadata)) {
          return new RoomOpenHelper.ValidationResult(false, "policy_metadata(com.org.callguard.data.db.entity.PolicyMetadataEntity).\n"
                  + " Expected:\n" + _infoPolicyMetadata + "\n"
                  + " Found:\n" + _existingPolicyMetadata);
        }
        final HashMap<String, TableInfo.Column> _columnsAuditEvents = new HashMap<String, TableInfo.Column>(8);
        _columnsAuditEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("direction", new TableInfo.Column("direction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("normalizedNumber", new TableInfo.Column("normalizedNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("decision", new TableInfo.Column("decision", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("matchedRule", new TableInfo.Column("matchedRule", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("blocklistVersion", new TableInfo.Column("blocklistVersion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditEvents.put("uploaded", new TableInfo.Column("uploaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAuditEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAuditEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAuditEvents = new TableInfo("audit_events", _columnsAuditEvents, _foreignKeysAuditEvents, _indicesAuditEvents);
        final TableInfo _existingAuditEvents = TableInfo.read(db, "audit_events");
        if (!_infoAuditEvents.equals(_existingAuditEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "audit_events(com.org.callguard.data.db.entity.AuditEventEntity).\n"
                  + " Expected:\n" + _infoAuditEvents + "\n"
                  + " Found:\n" + _existingAuditEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsAdminMessages = new HashMap<String, TableInfo.Column>(7);
        _columnsAdminMessages.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("body", new TableInfo.Column("body", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("issuedAt", new TableInfo.Column("issuedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("expiresAt", new TableInfo.Column("expiresAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAdminMessages.put("acknowledged", new TableInfo.Column("acknowledged", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAdminMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAdminMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAdminMessages = new TableInfo("admin_messages", _columnsAdminMessages, _foreignKeysAdminMessages, _indicesAdminMessages);
        final TableInfo _existingAdminMessages = TableInfo.read(db, "admin_messages");
        if (!_infoAdminMessages.equals(_existingAdminMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "admin_messages(com.org.callguard.data.db.entity.AdminMessageEntity).\n"
                  + " Expected:\n" + _infoAdminMessages + "\n"
                  + " Found:\n" + _existingAdminMessages);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "81f54286426f75ab8bf28865c9a87c05", "b7d46a04d447a985f9097d0c2a1afd1e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "blocked_numbers","allowlisted_numbers","blocklist_versions","sync_status","policy_metadata","audit_events","admin_messages");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `blocked_numbers`");
      _db.execSQL("DELETE FROM `allowlisted_numbers`");
      _db.execSQL("DELETE FROM `blocklist_versions`");
      _db.execSQL("DELETE FROM `sync_status`");
      _db.execSQL("DELETE FROM `policy_metadata`");
      _db.execSQL("DELETE FROM `audit_events`");
      _db.execSQL("DELETE FROM `admin_messages`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BlockedNumberDao.class, BlockedNumberDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AllowlistedNumberDao.class, AllowlistedNumberDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BlocklistVersionDao.class, BlocklistVersionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SyncStatusDao.class, SyncStatusDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PolicyMetadataDao.class, PolicyMetadataDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AuditEventDao.class, AuditEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AdminMessageDao.class, AdminMessageDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BlockedNumberDao blockedNumberDao() {
    if (_blockedNumberDao != null) {
      return _blockedNumberDao;
    } else {
      synchronized(this) {
        if(_blockedNumberDao == null) {
          _blockedNumberDao = new BlockedNumberDao_Impl(this);
        }
        return _blockedNumberDao;
      }
    }
  }

  @Override
  public AllowlistedNumberDao allowlistedNumberDao() {
    if (_allowlistedNumberDao != null) {
      return _allowlistedNumberDao;
    } else {
      synchronized(this) {
        if(_allowlistedNumberDao == null) {
          _allowlistedNumberDao = new AllowlistedNumberDao_Impl(this);
        }
        return _allowlistedNumberDao;
      }
    }
  }

  @Override
  public BlocklistVersionDao blocklistVersionDao() {
    if (_blocklistVersionDao != null) {
      return _blocklistVersionDao;
    } else {
      synchronized(this) {
        if(_blocklistVersionDao == null) {
          _blocklistVersionDao = new BlocklistVersionDao_Impl(this);
        }
        return _blocklistVersionDao;
      }
    }
  }

  @Override
  public SyncStatusDao syncStatusDao() {
    if (_syncStatusDao != null) {
      return _syncStatusDao;
    } else {
      synchronized(this) {
        if(_syncStatusDao == null) {
          _syncStatusDao = new SyncStatusDao_Impl(this);
        }
        return _syncStatusDao;
      }
    }
  }

  @Override
  public PolicyMetadataDao policyMetadataDao() {
    if (_policyMetadataDao != null) {
      return _policyMetadataDao;
    } else {
      synchronized(this) {
        if(_policyMetadataDao == null) {
          _policyMetadataDao = new PolicyMetadataDao_Impl(this);
        }
        return _policyMetadataDao;
      }
    }
  }

  @Override
  public AuditEventDao auditEventDao() {
    if (_auditEventDao != null) {
      return _auditEventDao;
    } else {
      synchronized(this) {
        if(_auditEventDao == null) {
          _auditEventDao = new AuditEventDao_Impl(this);
        }
        return _auditEventDao;
      }
    }
  }

  @Override
  public AdminMessageDao adminMessageDao() {
    if (_adminMessageDao != null) {
      return _adminMessageDao;
    } else {
      synchronized(this) {
        if(_adminMessageDao == null) {
          _adminMessageDao = new AdminMessageDao_Impl(this);
        }
        return _adminMessageDao;
      }
    }
  }
}
