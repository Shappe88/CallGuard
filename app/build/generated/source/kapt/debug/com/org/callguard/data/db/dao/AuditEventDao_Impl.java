package com.org.callguard.data.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.org.callguard.data.db.entity.AuditEventEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class AuditEventDao_Impl implements AuditEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AuditEventEntity> __insertionAdapterOfAuditEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfPurgeOlderThan;

  public AuditEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAuditEventEntity = new EntityInsertionAdapter<AuditEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `audit_events` (`id`,`timestamp`,`direction`,`normalizedNumber`,`decision`,`matchedRule`,`blocklistVersion`,`uploaded`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AuditEventEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        if (entity.getDirection() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDirection());
        }
        if (entity.getNormalizedNumber() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNormalizedNumber());
        }
        if (entity.getDecision() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDecision());
        }
        if (entity.getMatchedRule() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMatchedRule());
        }
        statement.bindLong(7, entity.getBlocklistVersion());
        final int _tmp = entity.getUploaded() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__preparedStmtOfPurgeOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM audit_events WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AuditEventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAuditEventEntity.insert(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object purgeOlderThan(final long cutoff, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoff);
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
          __preparedStmtOfPurgeOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnuploaded(final int limit,
      final Continuation<? super List<AuditEventEntity>> $completion) {
    final String _sql = "SELECT * FROM audit_events WHERE uploaded = 0 ORDER BY timestamp ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditEventEntity>>() {
      @Override
      @NonNull
      public List<AuditEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDecision = CursorUtil.getColumnIndexOrThrow(_cursor, "decision");
          final int _cursorIndexOfMatchedRule = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedRule");
          final int _cursorIndexOfBlocklistVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "blocklistVersion");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<AuditEventEntity> _result = new ArrayList<AuditEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDirection;
            if (_cursor.isNull(_cursorIndexOfDirection)) {
              _tmpDirection = null;
            } else {
              _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
            }
            final String _tmpNormalizedNumber;
            if (_cursor.isNull(_cursorIndexOfNormalizedNumber)) {
              _tmpNormalizedNumber = null;
            } else {
              _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
            }
            final String _tmpDecision;
            if (_cursor.isNull(_cursorIndexOfDecision)) {
              _tmpDecision = null;
            } else {
              _tmpDecision = _cursor.getString(_cursorIndexOfDecision);
            }
            final String _tmpMatchedRule;
            if (_cursor.isNull(_cursorIndexOfMatchedRule)) {
              _tmpMatchedRule = null;
            } else {
              _tmpMatchedRule = _cursor.getString(_cursorIndexOfMatchedRule);
            }
            final long _tmpBlocklistVersion;
            _tmpBlocklistVersion = _cursor.getLong(_cursorIndexOfBlocklistVersion);
            final boolean _tmpUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp != 0;
            _item = new AuditEventEntity(_tmpId,_tmpTimestamp,_tmpDirection,_tmpNormalizedNumber,_tmpDecision,_tmpMatchedRule,_tmpBlocklistVersion,_tmpUploaded);
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
  public Object getRecent(final int limit,
      final Continuation<? super List<AuditEventEntity>> $completion) {
    final String _sql = "SELECT * FROM audit_events ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditEventEntity>>() {
      @Override
      @NonNull
      public List<AuditEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfNormalizedNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "normalizedNumber");
          final int _cursorIndexOfDecision = CursorUtil.getColumnIndexOrThrow(_cursor, "decision");
          final int _cursorIndexOfMatchedRule = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedRule");
          final int _cursorIndexOfBlocklistVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "blocklistVersion");
          final int _cursorIndexOfUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "uploaded");
          final List<AuditEventEntity> _result = new ArrayList<AuditEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDirection;
            if (_cursor.isNull(_cursorIndexOfDirection)) {
              _tmpDirection = null;
            } else {
              _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
            }
            final String _tmpNormalizedNumber;
            if (_cursor.isNull(_cursorIndexOfNormalizedNumber)) {
              _tmpNormalizedNumber = null;
            } else {
              _tmpNormalizedNumber = _cursor.getString(_cursorIndexOfNormalizedNumber);
            }
            final String _tmpDecision;
            if (_cursor.isNull(_cursorIndexOfDecision)) {
              _tmpDecision = null;
            } else {
              _tmpDecision = _cursor.getString(_cursorIndexOfDecision);
            }
            final String _tmpMatchedRule;
            if (_cursor.isNull(_cursorIndexOfMatchedRule)) {
              _tmpMatchedRule = null;
            } else {
              _tmpMatchedRule = _cursor.getString(_cursorIndexOfMatchedRule);
            }
            final long _tmpBlocklistVersion;
            _tmpBlocklistVersion = _cursor.getLong(_cursorIndexOfBlocklistVersion);
            final boolean _tmpUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfUploaded);
            _tmpUploaded = _tmp != 0;
            _item = new AuditEventEntity(_tmpId,_tmpTimestamp,_tmpDirection,_tmpNormalizedNumber,_tmpDecision,_tmpMatchedRule,_tmpBlocklistVersion,_tmpUploaded);
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
  public Object markUploaded(final List<Long> ids, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("UPDATE audit_events SET uploaded = 1 WHERE id IN (");
        final int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (Long _item : ids) {
          if (_item == null) {
            _stmt.bindNull(_argIndex);
          } else {
            _stmt.bindLong(_argIndex, _item);
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
