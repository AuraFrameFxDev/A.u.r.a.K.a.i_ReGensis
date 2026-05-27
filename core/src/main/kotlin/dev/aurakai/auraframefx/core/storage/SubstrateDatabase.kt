package dev.aurakai.auraframefx.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TelemetryEntity::class], version = 3, exportSchema = false)
abstract class SubstrateDatabase : RoomDatabase() {
    abstract fun telemetryDao(): TelemetryDao

    companion object {
        @Volatile
        private var INSTANCE: SubstrateDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Compatibility for older v1 -> v2 path
                db.execSQL("ALTER TABLE lived_receipts ADD COLUMN sourceArchive TEXT")
                db.execSQL("ALTER TABLE lived_receipts ADD COLUMN originSignature TEXT DEFAULT 'SIGNATURE_NOT_FOUND'")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Ensure all v2 users have both columns with correct naming
                try {
                    db.execSQL("ALTER TABLE lived_receipts ADD COLUMN sourceArchive TEXT")
                } catch (e: Exception) {
                    // Column might already exist
                }
                try {
                    db.execSQL("ALTER TABLE lived_receipts ADD COLUMN originSignature TEXT DEFAULT 'SIGNATURE_NOT_FOUND'")
                } catch (e: Exception) {
                    // Column might already exist
                }
            }
        }

        fun getDatabase(context: Context): SubstrateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubstrateDatabase::class.java,
                    "aurakai_citadel_storage.db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
