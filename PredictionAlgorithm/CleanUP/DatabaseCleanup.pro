#-------------------------------------------------
#
# Project created by QtCreator 2015-12-02T13:54:36
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = DatabaseCleanup
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    database.cpp \
    cleanupwindow.cpp

HEADERS  += mainwindow.h \
    database.h \
    cleanupwindow.h

FORMS    += mainwindow.ui \
    database.ui \
    cleanupwindow.ui
