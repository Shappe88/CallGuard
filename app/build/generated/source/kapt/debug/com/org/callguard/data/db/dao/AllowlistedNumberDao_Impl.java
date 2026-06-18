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
import com.org.callguard.data.db.entity.AllowlistedNumberEntity;
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
public final class AllowlistedNumberDao_Impl implements AllowlistedNumberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AllowlistedNumberEntity> __insertionAdapterOfAllowlistedNumberEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNonOverrides;

  private final SharedSQLiteStatement __preparedStmtOfPurgeExpiredOverrides;

  public AllowlistedNumberDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAllowlistedNumberEntity = new EntityInsertionAdapter<AllowlistedNumberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `allowlisted_numbers` (`id`,`normalizedNumber`,`displayLabel`,`category`,`source`,`approvedBy`,`version`,`active`,`createdAt`,`updatedAt`,`expiresAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AllowlistedNumberEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNormalizedNumber() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNormalizedNumber());
        }
        if (entity.getDisplayLabel() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDisplayLabel());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategory());
        }
        if (entity.getSource() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSource());
        }
        if (entity.getApprovedBy() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getApprovedBy());
        }
        statement.bindLong(7, entity.getVersion());
        final int _tmp = entity.getActive() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
        if (entity.getExpiresAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getExpiresAt());
        }
      }
    };
    this.__preparedStmtOfDeleteNonOverrides = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM allowlisted_numbers WHERE category != 'OVERRIDE'";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeExpiredOverrides = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM allowlisted_numbers WHERE category = 'OVERRIDE' AND expiresAt <= ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<AllowlistedNumberEntity> entries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAllowlistedNumberEntity.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insert(final AllowlistedNumberEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAllowlistedNumberEntity.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteNonOverrides(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNonOverrides.acquire();
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
          __preparedStmtOfDeleteNonOverrides.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object purgeExpiredOverrides(final long now,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeExpiredOverrides.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, now);
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
          __preparedStmtOfPurgeExpiredOverrides.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object findActive(final String number, final long now,
      final Continuation<? super AllowlistedNumberEntity> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM allowlisted_numbers\n"
            + "        WHERE normalizedNumber = ?\n"
            + "          AND active = 1\n"
            + "          AND (expiresAt IS NULL OR expiresAt > ?)\n"
            + "        LIMIT 1\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (number == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, number);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, now);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AllowlistedNumberEntity>() {
      @Override
      @Nullable
      public AllowlistedNumberEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDisplayLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "displayLabel");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfApprovedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "approvedBy");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final AllowlistedNumberEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNormalizedNumber;
            if (_cursor.isNull(_cursorIndexOfNormalizedNumber)) {
              _tmpNormalizedNumber = null;
            } else {
              _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
            }
            final String _tmpDisplayLabel;
            if (_cursor.isNull(_cursorIndexOfDisplayLabel)) {
              _tmpDisplayLabel = null;
            } else {
              _tmpDisplayLabel = _cursor.getString(_cursorIndexOfDisplayLabel);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSource;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmpSource = null;
            } else {
              _tmpSource = _cursor.getString(_cursorIndexOfSource);
            }
            final String _tmpApprovedBy;
            if (_cursor.isNull(_cursorIndexOfApprovedBy)) {
              _tmpApprovedBy = null;
            } else {
              _tmpApprovedBy = _cursor.getString(_cursorIndexOfApprovedBy);
            }
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final boolean _tmpActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActive);
            _tmpActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpExpiresAt;
            if (_cursor.isNull(_cursorIndexOfExpiresAt)) {
              _tmpExpiresAt = null;
            } else {
              _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            }
            _result = new AllowlistedNumberEntity(_tmpId,_tmpNormalizedNumber,_tmpDisplayLabel,_tmpCategory,_tmpSource,_tmpApprovedBy,_tmpVersion,_tmpActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpExpiresAt);
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
  public Object getAll(final Continuation<? super List<AllowlistedNumberEntity>> $completion) {
    final String _sql = "SELECT * FROM allowlisted_numbers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AllowlistedNumberEntity>>() {
      @Override
      @NonNull
      public List<AllowlistedNumberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDisplayLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "displayLabel");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfApprovedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "approvedBy");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final List<AllowlistedNumberEntity> _result = new ArrayList<AllowlistedNumberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AllowlistedNumberEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNormalizedNumber;
            if (_cursor.isNull(_cursorIndexOfNormalizedNumber)) {
              _tmpNormalizedNumber = null;
            } else {
              _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
            }
            final String _tmpDisplayLabel;
            if (_cursor.isNull(_cursorIndexOfDisplayLabel)) {
              _tmpDisplayLabel = null;
            } else {
              _tmpDisplayLabel = _cursor.getString(_cursorIndexOfDisplayLabel);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSource;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmpSource = null;
            } else {
              _tmpSource = _cursor.getString(_cursorIndexOfSource);
            }
            final String _tmpApprovedBy;
            if (_cursor.isNull(_cursorIndexOfApprovedBy)) {
              _tmpApprovedBy = null;
            } else {
              _tmpApprovedBy = _cursor.getString(_cursorIndexOfApprovedBy);
            }
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final boolean _tmpActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActive);
            _tmpActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final Long _tmpExpiresAt;
            if (_cursor.isNull(_cursorIndexOfExpiresAt)) {
              _tmpExpiresAt = null;
            } else {
              _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            }
            _item = new AllowlistedNumberEntity(_tmpId,_tmpNormalizedNumber,_tmpDisplayLabel,_tmpCategory,_tmpSource,_tmpApprovedBy,_tmpVersion,_tmpActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpExpiresAt);
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
