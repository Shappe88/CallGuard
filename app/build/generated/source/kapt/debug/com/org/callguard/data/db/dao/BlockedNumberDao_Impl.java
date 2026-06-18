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
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.org.callguard.data.db.entity.BlockedNumberEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
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
public final class BlockedNumberDao_Impl implements BlockedNumberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BlockedNumberEntity> __insertionAdapterOfBlockedNumberEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public BlockedNumberDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBlockedNumberEntity = new EntityInsertionAdapter<BlockedNumberEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `blocked_numbers` (`id`,`normalizedNumber`,`displayLabel`,`reasonCode`,`severity`,`source`,`version`,`signatureStatus`,`active`,`createdAt`,`updatedAt`,`expiresAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlockedNumberEntity entity) {
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
        if (entity.getReasonCode() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getReasonCode());
        }
        if (entity.getSeverity() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSeverity());
        }
        if (entity.getSource() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSource());
        }
        statement.bindLong(7, entity.getVersion());
        if (entity.getSignatureStatus() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getSignatureStatus());
        }
        final int _tmp = entity.getActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        if (entity.getExpiresAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getExpiresAt());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM blocked_numbers";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<BlockedNumberEntity> entries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBlockedNumberEntity.insert(entries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object findActive(final String number,
      final Continuation<? super BlockedNumberEntity> $completion) {
    final String _sql = "SELECT * FROM blocked_numbers WHERE normalizedNumber = ? AND active = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (number == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, number);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BlockedNumberEntity>() {
      @Override
      @Nullable
      public BlockedNumberEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDisplayLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "displayLabel");
          final int _cursorIndexOfReasonCode = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonCode");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfSignatureStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureStatus");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final BlockedNumberEntity _result;
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
            final String _tmpReasonCode;
            if (_cursor.isNull(_cursorIndexOfReasonCode)) {
              _tmpReasonCode = null;
            } else {
              _tmpReasonCode = _cursor.getString(_cursorIndexOfReasonCode);
            }
            final String _tmpSeverity;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmpSeverity = null;
            } else {
              _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            }
            final String _tmpSource;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmpSource = null;
            } else {
              _tmpSource = _cursor.getString(_cursorIndexOfSource);
            }
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final String _tmpSignatureStatus;
            if (_cursor.isNull(_cursorIndexOfSignatureStatus)) {
              _tmpSignatureStatus = null;
            } else {
              _tmpSignatureStatus = _cursor.getString(_cursorIndexOfSignatureStatus);
            }
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
            _result = new BlockedNumberEntity(_tmpId,_tmpNormalizedNumber,_tmpDisplayLabel,_tmpReasonCode,_tmpSeverity,_tmpSource,_tmpVersion,_tmpSignatureStatus,_tmpActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpExpiresAt);
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
  public Object activeCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM blocked_numbers WHERE active = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Object getAll(final Continuation<? super List<BlockedNumberEntity>> $completion) {
    final String _sql = "SELECT * FROM blocked_numbers";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BlockedNumberEntity>>() {
      @Override
      @NonNull
      public List<BlockedNumberEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDisplayLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "displayLabel");
          final int _cursorIndexOfReasonCode = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonCode");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfSignatureStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureStatus");
          final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final List<BlockedNumberEntity> _result = new ArrayList<BlockedNumberEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlockedNumberEntity _item;
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
            final String _tmpReasonCode;
            if (_cursor.isNull(_cursorIndexOfReasonCode)) {
              _tmpReasonCode = null;
            } else {
              _tmpReasonCode = _cursor.getString(_cursorIndexOfReasonCode);
            }
            final String _tmpSeverity;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmpSeverity = null;
            } else {
              _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            }
            final String _tmpSource;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmpSource = null;
            } else {
              _tmpSource = _cursor.getString(_cursorIndexOfSource);
            }
            final long _tmpVersion;
            _tmpVersion = _cursor.getLong(_cursorIndexOfVersion);
            final String _tmpSignatureStatus;
            if (_cursor.isNull(_cursorIndexOfSignatureStatus)) {
              _tmpSignatureStatus = null;
            } else {
              _tmpSignatureStatus = _cursor.getString(_cursorIndexOfSignatureStatus);
            }
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
            _item = new BlockedNumberEntity(_tmpId,_tmpNormalizedNumber,_tmpDisplayLabel,_tmpReasonCode,_tmpSeverity,_tmpSource,_tmpVersion,_tmpSignatureStatus,_tmpActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpExpiresAt);
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

  @Override
  public Object deleteByNumbers(final List<String> numbers,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("DELETE FROM blocked_numbers WHERE normalizedNumber IN (");
        final int _inputSize = numbers.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (String _item : numbers) {
          if (_item == null) {
            _stmt.bindNull(_argIndex);
          } else {
            _stmt.bindString(_argIndex, _item);
          }
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
