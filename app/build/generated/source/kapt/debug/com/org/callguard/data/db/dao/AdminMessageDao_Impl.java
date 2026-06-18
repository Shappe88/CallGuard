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
import androidx.sqlite.db.SupportSQLiteStatement;
import com.org.callguard.data.db.entity.AdminMessageEntity;
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
public final class AdminMessageDao_Impl implements AdminMessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AdminMessageEntity> __insertionAdapterOfAdminMessageEntity;

  private final SharedSQLiteStatement __preparedStmtOfAcknowledge;

  public AdminMessageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAdminMessageEntity = new EntityInsertionAdapter<AdminMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `admin_messages` (`id`,`title`,`body`,`severity`,`issuedAt`,`expiresAt`,`acknowledged`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AdminMessageEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getBody() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBody());
        }
        if (entity.getSeverity() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSeverity());
        }
        statement.bindLong(5, entity.getIssuedAt());
        if (entity.getExpiresAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getExpiresAt());
        }
        final int _tmp = entity.getAcknowledged() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfAcknowledge = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE admin_messages SET acknowledged = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<AdminMessageEntity> messages,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAdminMessageEntity.insert(messages);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object acknowledge(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAcknowledge.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
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
          __preparedStmtOfAcknowledge.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getActive(final long now,
      final Continuation<? super List<AdminMessageEntity>> $completion) {
    final String _sql = "SELECT * FROM admin_messages WHERE acknowledged = 0 AND (expiresAt IS NULL OR expiresAt > ?) ORDER BY issuedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, now);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AdminMessageEntity>>() {
      @Override
      @NonNull
      public List<AdminMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfIssuedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "issuedAt");
          final int _cursorIndexOfExpiresAt = CursorUtil.getColumnIndexOrThrow(_cursor, "expiresAt");
          final int _cursorIndexOfAcknowledged = CursorUtil.getColumnIndexOrThrow(_cursor, "acknowledged");
          final List<AdminMessageEntity> _result = new ArrayList<AdminMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AdminMessageEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpBody;
            if (_cursor.isNull(_cursorIndexOfBody)) {
              _tmpBody = null;
            } else {
              _tmpBody = _cursor.getString(_cursorIndexOfBody);
            }
            final String _tmpSeverity;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmpSeverity = null;
            } else {
              _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            }
            final long _tmpIssuedAt;
            _tmpIssuedAt = _cursor.getLong(_cursorIndexOfIssuedAt);
            final Long _tmpExpiresAt;
            if (_cursor.isNull(_cursorIndexOfExpiresAt)) {
              _tmpExpiresAt = null;
            } else {
              _tmpExpiresAt = _cursor.getLong(_cursorIndexOfExpiresAt);
            }
            final boolean _tmpAcknowledged;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAcknowledged);
            _tmpAcknowledged = _tmp != 0;
            _item = new AdminMessageEntity(_tmpId,_tmpTitle,_tmpBody,_tmpSeverity,_tmpIssuedAt,_tmpExpiresAt,_tmpAcknowledged);
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
