#ifndef DATABASE_H
#define DATABASE_H

#include <QWidget>
#include <QFile>

namespace Ui {
class Database;
}

struct Tuple
{
    /*
        Index : Value
        0 : Country
        1 : Region
        2 : Key
        3 - 26 : Value per Year
    */

     QVector<QString> tuple;
};

struct Data
{
    QString key;
    QVector <QString> dataPerYear;
};

struct Country
{
    QVector<Data> data;
    int numberOfKeys;
    QString country;
    QString region;
};



// ##################################### CLASS ###########################################
class Database : public QWidget
{
    Q_OBJECT


// PUBLIC CLASSES ************************************************************************
public:
    explicit Database(QWidget *parent = 0);
    ~Database(); // Deconstructor

    bool openDatabaseCsvFile(); // Opens the database csv file from specified directory.

    bool saveAsNewDatabase(); // Save the database from two-dimentional array into new
                              // csv file.

    QVector<Tuple> getDatabaseArray() {return databaseArray;}

    void refactor(QVector<QString> keysToKeep);

// ***************************************************************************************




// PRIVATE CLASSES ***********************************************************************
private:
    Ui::Database *ui;

    QVector <Tuple> databaseArray;

    bool deconstruct(QFile *file); // Converts the database in a two-dimentional array.

// ***************************************************************************************
};
// #######################################################################################


#endif // DATABASE_H
