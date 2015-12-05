#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <database.h>
#include <cleanupwindow.h>
#include <QTableWidget>

namespace Ui {
class MainWindow;
}




// ######################################################## CLASS ###########################################
class MainWindow : public QMainWindow
{
    Q_OBJECT








public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();








private slots:
    void on_openCsvFileButton_clicked();
    void on_cleanUpButton_clicked();








    void on_saveAsNewCsvFileButton_clicked();

    void on_fillInValuesButton_clicked();

public slots:
    void removeKeys(QVector<QString>);

private:
    Ui::MainWindow  *ui;

    Database        *database;
    CleanUpWindow   *cleanUpWindow;

    QVector<Tuple> databaseArray;

    QTableWidget *databaseTable;

    QVector<QString> importantKeys;

    void printDatabase(QVector<Tuple>);

    bool keyIsImportant(QString key);

    int myrand(int n, int m );


};

#endif // MAINWINDOW_H
