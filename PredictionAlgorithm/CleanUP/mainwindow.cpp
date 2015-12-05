#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <database.h>
#include <QFileDialog>
#include <QDir>
#include <QDebug>
#include <QTextStream>
#include <QTime>
#include <QtMath>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    setWindowTitle("Database Cleanup Tool : Krystian Dobkowski & Richard Carragher");

    ui->cleanUpButton->setEnabled(false);
    ui->saveAsNewCsvFileButton->setEnabled(false);

    databaseTable = new QTableWidget();
    ui->dbTableLayout->addWidget(databaseTable);

    database        = NULL;
    cleanUpWindow   = NULL;

    importantKeys.append("CO2 emissions from gaseous fuel consumption (% of total)");
    importantKeys.append("CO2 emissions from liquid fuel consumption (% of total)");
    importantKeys.append("CO2 emissions from solid fuel consumption (% of total)");
    importantKeys.append("Methane emissions (kt of CO2 equivalent)");
    importantKeys.append("Nitrous oxide emissions (thousand metric tons of CO2 equivalent)");
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_openCsvFileButton_clicked()
{
    if(database != NULL)
        delete database;

    database = new Database();

    bool ret = database->openDatabaseCsvFile();

    if(ret == false) return;

    databaseArray = database->getDatabaseArray();

    delete databaseTable;
    databaseTable = new QTableWidget();
    ui->dbTableLayout->addWidget(databaseTable);

    printDatabase(databaseArray);

    ui->cleanUpButton->setEnabled(true);
    ui->saveAsNewCsvFileButton->setEnabled(true);
}

void MainWindow::on_cleanUpButton_clicked()
{
    if(cleanUpWindow != NULL)
    {
        disconnect (cleanUpWindow, SIGNAL(removeWords(QVector<QString>)), this, SLOT(removeKeys(QVector<QString>)));
        delete cleanUpWindow;
    }

    cleanUpWindow = new CleanUpWindow();
    connect(cleanUpWindow, SIGNAL(removeWords(QVector<QString>)), this, SLOT(removeKeys(QVector<QString>)));

    cleanUpWindow->show();


}

void MainWindow::removeKeys(QVector<QString> keysToRemove)
{
        for(int i = 0; i < databaseArray.size(); i++)
        {
            for(int j = 0; j < keysToRemove.size(); j++)
            {
                qDebug() << "---> " << i << " : " << j;
                if(databaseArray.at(i).tuple.contains(keysToRemove.at(j)))
                {
                    databaseArray.removeAt(i);
                    i--;
                    qDebug() << "############################# DELETED ###################################";
                }
            }
        }

    printDatabase(databaseArray);
}

void MainWindow::printDatabase(QVector<Tuple> database)
{
    delete databaseTable;
    databaseTable = new QTableWidget();
    ui->dbTableLayout->addWidget(databaseTable);

    for(int i = 0; i < database.size(); i++)
    {
        databaseTable->insertRow(i);

        for(int j = 0; j < database.at(i).tuple.size(); j++)
        {
            if(i == 0)
                databaseTable->insertColumn(j);
            QTableWidgetItem *item = new QTableWidgetItem (database.at(i).tuple.at(j));
            databaseTable->setItem(i,j,item);
        }
    }
}

void MainWindow::on_saveAsNewCsvFileButton_clicked()
{
    QString savePath = QFileDialog::getExistingDirectoryUrl().toString().remove("file:///");

    QFile *newCSV = new QFile(QString("%1/Cleared Database.csv").arg(savePath));


    if(!newCSV->open(QIODevice::ReadWrite))
        return;
    else
    {
        QTextStream stream(newCSV);

        for(int i = 0; i < databaseArray.size(); i++)
        {
            QString line;
            for(int j = 0; j < databaseArray.at(i).tuple.size(); j++)
            {
                if(j == (databaseArray.at(i).tuple.size() - 1))
                    line.append(databaseArray.at(i).tuple.at(j));
                else
                    line.append(QString("%1;").arg(databaseArray.at(i).tuple.at(j)));
            }
            stream << line << "\n";
        }
    }

    newCSV->close();
}

void MainWindow::on_fillInValuesButton_clicked()
{
    bool fieldTotallyEmpty = false;

    QVector<QString> countriesToRemove;

    for(int i = 0; i < databaseArray.size(); i++)
    {
        int deleteCount = 3;
        for(int j = 3; j < databaseArray.at(i).tuple.size(); j++)
        {
            if(databaseArray.at(i).tuple.at(j) == "")
            {

               databaseArray[i].tuple[j] = "NULL";
               deleteCount++;

               if(deleteCount == (databaseArray.at(i).tuple.size()) && (countriesToRemove.contains(databaseArray.at(i).tuple.at(0))) == false
               && keyIsImportant(databaseArray.at(i).tuple.at(3)))
              {
                   countriesToRemove.append(databaseArray.at(i).tuple.at(0));
                   deleteCount = 3;
              }
            }
        }
    }

    // HERE IM REMOVING COUNTRIES THAT ARE MISSING IMPORTANT VALUES +++++++++++++++++++++++++++++++++++++++++++
    for(int i = 0; i < databaseArray.size(); i++)
    {
        for(int j = 0; j < countriesToRemove.size(); j++)
        {
            if(databaseArray.at(i).tuple.at(0) == countriesToRemove.at(j))
            {
                databaseArray.remove(i);
                i == 0 ? i = -1 : i--;
                qDebug() << "Just removed " << countriesToRemove.at(j) << " cause it lacks necesairy data.";
            }
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    int nullCounter = 0;
    for(int i = 0; i < databaseArray.size(); i++)
    {
        for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
        {
            if(databaseArray.at(i).tuple.at(j).contains("NULL"))
            {
                nullCounter++;
            }
        }
    }

    qDebug() << "NULL COUNTER === BEFORE === " << nullCounter;

    // for the entire database do:
    for(int i = 0; i < databaseArray.size(); i++)
    {

        int tracker = 3;
        for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
        {
            if(databaseArray.at(i).tuple.at(j) == "NULL")
            {
                tracker++;
            }
        }

        if(tracker == databaseArray.at(i).tuple.size())
        {
            for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
            {
                databaseArray[i].tuple[j] = "0";
            }
        }
        else
        {

            //for the entire tuple do:
            for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
            {

                // ==================== handling of if(NULL) begins here ==========================
                // if the current cell is NULL do:
                if(databaseArray.at(i).tuple.at(j) == "NULL")
                {
                    // if it's the last cell do:
                    if(j == (databaseArray.at(i).tuple.size() - 1))
                    {
                        // if the cell before it is not NULL then calculate it
                        if(databaseArray.at(i).tuple.at(j-1) != "NULL")
                        {
                            int ret = myrand(1,2);

                            double randomPercentage = myrand(1,20) / 100.0;
                            qDebug() << "PERCENTAGE = " << randomPercentage;

                            if(ret == 1)
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j-1).toDouble() + (databaseArray.at(i).tuple.at(j-1).toDouble() * randomPercentage));
                            else
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j-1).toDouble() - (databaseArray.at(i).tuple.at(j-1).toDouble() * randomPercentage));
                        }
                    }
                    else // else if you're not the lst cell
                    {
                        int random = myrand(1,20);
                        qDebug() << "RANDOM ===== " << random;
                        double randomPercentage = random / 100.0;
                        qDebug() << "PERCENTAGE = " << randomPercentage;

                        // if the cell bahind you isn't NULL do:
                        if(databaseArray.at(i).tuple.at(j-1) != "NULL" && j-1 != 2)
                        {
                            int ret = myrand(1,2);

                            if(ret == 1)
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j-1).toDouble() + (databaseArray.at(i).tuple.at(j-1).toDouble() * randomPercentage));
                            else
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j-1).toDouble() - (databaseArray.at(i).tuple.at(j-1).toDouble() * randomPercentage));
                        }
                        // else if the cell in front of you isn't NULL do:
                        else if(databaseArray.at(i).tuple.at(j+1) != "NULL")
                        {
                            int ret = myrand(1,2);

                            if(ret == 1)
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j+1).toDouble() + (databaseArray.at(i).tuple.at(j+1).toDouble() * randomPercentage));
                            else
                                databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(j+1).toDouble() - (databaseArray.at(i).tuple.at(j+1).toDouble() * randomPercentage));
                        }
                        // otherwise do:
                        else
                        {
                            int count = j;
                            while(count < databaseArray.at(i).tuple.size() - 1)
                            {
                                if(databaseArray.at(i).tuple.at(count + 1).contains("NULL"))
                                {
                                    count++;
                                    if(count < databaseArray.at(i).tuple.size() - 1)
                                    {
                                        for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
                                        {
                                            databaseArray[i].tuple[j] = "0";
                                        }
                                        break;
                                    }
                                }
                                else
                                {
                                    int ret = myrand(1,2);

                                    if(ret == 1)
                                        databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(count).toDouble() + (databaseArray.at(i).tuple.at(count).toDouble() * (myrand(1,20) / 100 )));
                                    else
                                        databaseArray[i].tuple[j] = QString("%1").arg(databaseArray.at(i).tuple.at(count).toDouble() - (databaseArray.at(i).tuple.at(count).toDouble() * (myrand(1,20) / 100 )));

                                    break;
                                }
                            }
                        }
                    }
                } // ============ handling of if(NULL) ends here ===================================
            }

        }
    }

    nullCounter = 0;
    for(int i = 0; i < databaseArray.size(); i++)
    {
        for(int j=3; j < databaseArray.at(i).tuple.size(); j++)
        {
            if(databaseArray.at(i).tuple.at(j).contains("NULL"))
            {
                nullCounter++;
            }
        }
    }

    qDebug() << "NULL COUNTER === AFTER === " << nullCounter;


    printDatabase(databaseArray);
}

bool MainWindow::keyIsImportant(QString key)
{
    importantKeys.contains(key);
}

int MainWindow::myrand( int n,  int m )
{
    QTime time = QTime::currentTime();
    qsrand((uint)time.msec());

    return qrand() % ((m + 1) - n) + n;
}
