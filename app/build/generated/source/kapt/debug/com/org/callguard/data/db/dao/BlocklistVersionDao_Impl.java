package com.org.callguard.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.org.callguard.data.db.entity.BlocklistVersionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BlocklistVersionDao_Impl implements BlocklistVersionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BlocklistVersionEntity> __insertionAdapterOfBlocklistVersionEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public BlocklistVersionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBlocklistVersionEntity = new EntityInsertionAdapter<BlocklistVersionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `blocklist_versions` (`version`,`releasedAt`,`signature`,`checksum`,`appliedAt`,`status`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlocklistVersionEntity entity) {
        statement.bindLong(1, entity.getVersion());
        statement.bindLong(2, entity.getReleasedAt());
        if (entity.getSignature() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSignature());
        }
        if (entity.getChecksum() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getChecksum());
        }
        if (entity.getAppliedAt() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getAppliedAt());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getStatus());
        }
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE blocklist_versions SET status = ?, appliedAt = ? WHERE version = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BlocklistVersionEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBlocklistVersionEntity.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final long version, final String status, final Long appliedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (appliedAt == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, appliedAt);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, version);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getCurrentApplied(final Continuation<? super BlocklistVersionEntity> $completion) {
    final String _sql = "SELECT * FROM blocklist_versions WHERE status = 'APPLIED' ORDER BY version DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BlocklistVersionEntity>() {
      @Override
      @Nullable
      public BlocklistVersionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfReleasedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "releasedAt");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfChecksum = CursorUtil.getColumnIndexOrThrow(_cursor, "checksum");
          final int _cursorIndexOfAppliedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final BlocklistVersionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final long _tmpReleasedAt;
            _tmpReleasedAt = _cursor.getLong(_cursorIndexOfReleasedAt);
            final String _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            }
            final String _tmpChecksum;
            if (_cursor.isNull(_cursorIndexOfChecksum)) {
              _tmpChecksum = null;
            } else {
              _tmpChecksum = _cursor.getString(_cursorIndexOfChecksum);
            }
            final Long _tmpAppliedAt;
            if (_cursor.isNull(_cursorIndexOfAppliedAt)) {
              _tmpAppliedAt = null;
            } else {
              _tmpAppliedAt = _cursor.getLong(_cursorIndexOfAppliedAt);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _result = new BlocklistVersionEntity(_tmpVersion,_tmpReleasedAt,_tmpSignature,_tmpChecksum,_tmpAppliedAt,_tmpStatus);
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
  public Object getAll(final Continuation<? super List<BlocklistVersionEntity>> $completion) {
    final String _sql = "SELECT * FROM blocklist_versions ORDER BY version DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BlocklistVersionEntity>>() {
      @Override
      @NonNull
      public List<BlocklistVersionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfReleasedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "releasedAt");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfChecksum = CursorUtil.getColumnIndexOrThrow(_cursor, "checksum");
          final int _cursorIndexOfAppliedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "appliedAt");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<BlocklistVersionEntity> _result = new ArrayList<BlocklistVersionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlocklistVersionEntity _item;
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final long _tmpReleasedAt;
            _tmpReleasedAt = _cursor.getLong(_cursorIndexOfReleasedAt);
            final String _tmpSignature;
            if (_cursor.isNull(_cursorIndexOfSignature)) {
              _tmpSignature = null;
            } else {
              _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            }
            final String _tmpChecksum;
            if (_cursor.isNull(_cursorIndexOfChecksum)) {
              _tmpChecksum = null;
            } else {
              _tmpChecksum = _cursor.getString(_cursorIndexOfChecksum);
            }
            final Long _tmpAppliedAt;
            if (_cursor.isNull(_cursorIndexOfAppliedAt)) {
              _tmpAppliedAt = null;
            } else {
              _tmpAppliedAt = _cursor.getLong(_cursorIndexOfAppliedAt);
            }
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new BlocklistVersionEntity(_tmpVersion,_tmpReleasedAt,_tmpSignature,_tmpChecksum,_tmpAppliedAt,_tmpStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
