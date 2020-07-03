package com.example.ubbapp.repository.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

/**
 * The Database class describes the references to the database
 */
public class Database {

    /**
     * The Database attributes
     */
    private DatabaseReference database;
    private StorageReference storage;

    /**
     * The Database constructor, which need only the database reference as parameter
     *
     * @param database DatabaseReference, the reference to the database
     */
    public Database(DatabaseReference database) {
        this.database = database;
    }

    /**
     * The Database constructor, which initializes both the database reference as well as
     * the storage reference
     *
     * @param database DatabaseReference, the reference to the database
     * @param storage  StorageReference, the reference to the storage
     */
    public Database(DatabaseReference database, StorageReference storage) {
        this.database = database;
        this.storage = storage;
    }

    /**
     * The getDatabase method returns the DatabaseReference of the Database
     *
     * @return DatabaseReference, the reference to the database
     */
    public DatabaseReference getDatabase() {
        return database;
    }

    /**
     * The setDatabase method sets a new DatabaseReference for the Database
     *
     * @param database DatabaseReference, which will be the new database reference
     */
    public void setDatabase(DatabaseReference database) {
        this.database = database;
    }

    /**
     * The getStorage method returns the StorageReference of the Database
     *
     * @return StorageReference, the reference of the storage
     */
    public StorageReference getStorage() {
        return storage;
    }

    /**
     * The setStorage method sets a new StorageReference for the Database
     *
     * @param storage StorageReference, which will be the new storage reference
     */
    public void setStorage(StorageReference storage) {
        this.storage = storage;
    }
}
