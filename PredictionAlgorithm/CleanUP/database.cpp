#include "database.h"
#include "ui_database.h"
#include <QFile>
#include <QFileDialog>
#include <QDir>
#include <QDebug>
#include <QTextStream>

Database::Database(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Database)
{
    ui->setupUi(this);
}

Database::~Database()
{
    delete ui;
}


// PUBLIC FUNCTION IMPLEMENTATIONS #############################################################################

bool Database::openDatabaseCsvFile()
{
    QFile *csvFile;

    try
    {
        QString fileURL = QFileDialog().getOpenFileUrl().toString();
        fileURL = fileURL.remove("file:///");
        csvFile = new QFile(fileURL);
    }
    catch(int e)
    {
        qDebug() << QString("Error %1 : Could not open the specified file. File corrupted or in use by another software.").arg(e);
        return false;
    }

    deconstruct(csvFile);

    return true;
}


// #############################################################################################################












// PRIVATE FUNCTION IMEPLEMENTATIONS ############################################################################


bool Database::deconstruct(QFile *file)
{
    if(file->open(QIODevice::ReadWrite) == false)
        return false;

    QTextStream *stream = new QTextStream(file);

    while(stream->atEnd() == false)
    {
        Tuple tempTuple;
        QStringList tempList = stream->readLine().split(";");

        for(int i = 0; i < tempList.size(); i++)
        {
            tempTuple.tuple.append(tempList.at(i));
        }

        databaseArray.append(tempTuple);
    }

    file->close();
    delete stream;

    return true;
}
