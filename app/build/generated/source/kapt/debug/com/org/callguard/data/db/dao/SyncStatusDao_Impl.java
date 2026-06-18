package com.org.callguard.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.org.callguard.data.db.entity.SyncStatusEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SyncStatusDao_Impl implements SyncStatusDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SyncStatusEntity> __insertionAdapterOfSyncStatusEntity;

  public SyncStatusDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSyncStatusEntity = new EntityInsertionAdapter<SyncStatusEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sync_status` (`id`,`lastSyncAttempt`,`lastSuccessfulSync`,`currentVersion`,`pendingVersion`,`lastError`,`deviceId`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SyncStatusEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getLastSyncAttempt() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getLastSyncAttempt());
        }
        if (entity.getLastSuccessfulSync() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getLastSuccessfulSync());
        }
        statement.bindLong(4, entity.getCurrentVersion());
        if (entity.getPendingVersion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getPendingVersion());
        }
        if (entity.getLastError() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLastError());
        }
        if (entity.getDeviceId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDeviceId());
        }
      }
    };
  }

  @Override
  public Object upsert(final SyncStatusEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSyncStatusEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object get(final Continuation<? super SyncStatusEntity> $completion) {
    final String _sql = "SELECT * FROM sync_status WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SyncStatusEntity>() {
      @Override
      @Nullable
      public SyncStatusEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLastSyncAttempt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAttempt");
          final int _cursorIndexOfLastSuccessfulSync = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSuccessfulSync");
          final int _cursorIndexOfCurrentVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "currentVersion");
          final int _cursorIndexOfPendingVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingVersion");
          final int _cursorIndexOfLastError = CursorUtil.getColumnIndexOrThrow(_cursor, "lastError");
          final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
          final SyncStatusEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Long _tmpLastSyncAttempt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAttempt)) {
              _tmpLastSyncAttempt = null;
            } else {
              _tmpLastSyncAttempt = _cursor.getLong(_cursorIndexOfLastSyncAttempt);
            }
            final Long _tmpLastSuccessfulSync;
            if (_cursor.isNull(_cursorIndexOfLastSuccessfulSync)) {
              _tmpLastSuccessfulSync = null;
            } else {
              _tmpLastSuccessfulSync = _cursor.getLong(_cursorIndexOfLastSuccessfulSync);
            }
            final long _tmpCurrentVersion;
            _tmpCurrentVersion = _cursor.getLong(_cursorIndexOfCurrentVersion);
            final Long _tmpPendingVersion;
            if (_cursor.isNull(_cursorIndexOfPendingVersion)) {
              _tmpPendingVersion = null;
            } else {
              _tmpPendingVersion = _cursor.getLong(_cursorIndexOfPendingVersion);
            }
            final String _tmpLastError;
            if (_cursor.isNull(_cursorIndexOfLastError)) {
              _tmpLastError = null;
            } else {
              _tmpLastError = _cursor.getString(_cursorIndexOfLastError);
            }
            final String _tmpDeviceId;
            if (_cursor.isNull(_cursorIndexOfDeviceId)) {
              _tmpDeviceId = null;
            } else {
              _tmpDeviceId = _cursor.getString(_cursorIndexOfDeviceId);
            }
            _result = new SyncStatusEntity(_tmpId,_tmpLastSyncAttempt,_tmpLastSuccessfulSync,_tmpCurrentVersion,_tmpPendingVersion,_tmpLastError,_tmpDeviceId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SyncStatusEntity> observe() {
    final String _sql = "SELECT * FROM sync_status WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sync_status"}, new Callable<SyncStatusEntity>() {
      @Override
      @Nullable
      public SyncStatusEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLastSyncAttempt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAttempt");
          final int _cursorIndexOfLastSuccessfulSync = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSuccessfulSync");
          final int _cursorIndexOfCurrentVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "currentVersion");
          final int _cursorIndexOfPendingVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "pendingVersion");
          final int _cursorIndexOfLastError = CursorUtil.getColumnIndexOrThrow(_cursor, "lastError");
          final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
          final SyncStatusEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Long _tmpLastSyncAttempt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAttempt)) {
              _tmpLastSyncAttempt = null;
            } else {
              _tmpLastSyncAttempt = _cursor.getLong(_cursorIndexOfLastSyncAttempt);
            }
            final Long _tmpLastSuccessfulSync;
            if (_cursor.isNull(_cursorIndexOfLastSuccessfulSync)) {
              _tmpLastSuccessfulSync = null;
            } else {
              _tmpLastSuccessfulSync = _cursor.getLong(_cursorIndexOfLastSuccessfulSync);
            }
            final long _tmpCurrentVersion;
            _tmpCurrentVersion = _cursor.getLong(_cursorIndexOfCurrentVersion);
            final Long _tmpPendingVersion;
            if (_cursor.isNull(_cursorIndexOfPendingVersion)) {
              _tmpPendingVersion = null;
            } else {
              _tmpPendingVersion = _cursor.getLong(_cursorIndexOfPendingVersion);
            }
            final String _tmpLastError;
            if (_cursor.isNull(_cursorIndexOfLastError)) {
              _tmpLastError = null;
            } else {
              _tmpLastError = _cursor.getString(_cursorIndexOfLastError);
            }
            final String _tmpDeviceId;
            if (_cursor.isNull(_cursorIndexOfDeviceId)) {
              _tmpDeviceId = null;
            } else {
              _tmpDeviceId = _cursor.getString(_cursorIndexOfDeviceId);
            }
            _result = new SyncStatusEntity(_tmpId,_tmpLastSyncAttempt,_tmpLastSuccessfulSync,_tmpCurrentVersion,_tmpPendingVersion,_tmpLastError,_tmpDeviceId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
