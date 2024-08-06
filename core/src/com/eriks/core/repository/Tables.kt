package com.eriks.core.repository

object Tables {

    const val DATABASE_NAME = "csav2.db"

    const val CREATE_TABLE_PACKAGE = "CREATE TABLE IF NOT EXISTS PACKAGE(id text primary key, is_open bool, origin text, timestamp NUMERIC, type text)"
    const val DROP_TABLE_PACKAGE = "DROP TABLE PACKAGE"

    const val CREATE_TABLE_PARAM = "CREATE TABLE IF NOT EXISTS PARAM(name text primary key, value text)"
    const val DROP_TABLE_PARAM = "DROP TABLE PARAM"

    const val CREATE_TABLE_STICKER = "CREATE TABLE IF NOT EXISTS STICKER(id text primary key, blueprint text, float text, value NUMERIC, is_glued bool)"
    const val DROP_TABLE_STICKER = "DROP TABLE STICKER"

    const val CREATE_TABLE_COLLECTION_TASK = "CREATE TABLE IF NOT EXISTS COLLECTION_TASK(collection_task text, is_claimed bool)"
    const val DROP_TABLE_COLLECTION_TASK = "DROP TABLE COLLECTION_TASK"

    const val CREATE_TABLE_PROGRESSION_TASK = "CREATE TABLE IF NOT EXISTS PROGRESSION_TASK(id text primary key, param_name text, task_description text, milestone numeric, is_completed bool, is_claimed bool, reward numeric)"
    const val DROP_TABLE_PROGRESSION_TASK = "DROP TABLE PROGRESSION_TASK"
}