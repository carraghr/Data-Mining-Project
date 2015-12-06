#ifndef CLEANUPWINDOW_H
#define CLEANUPWINDOW_H

#include <QWidget>

namespace Ui {
class CleanUpWindow;
}

class CleanUpWindow : public QWidget
{
    Q_OBJECT

public:
    explicit CleanUpWindow(QWidget *parent = 0);
    ~CleanUpWindow();

private slots:
    void on_keyLineEdit_textChanged(const QString &arg1);

    void on_addKeyToRemoveButton_clicked();

    void on_acceptButton_clicked();

    void on_cancelButton_clicked();

private:
    Ui::CleanUpWindow *ui;

    QVector<QString> keys;

signals:
    void removeWords(QVector<QString>);
};

#endif // CLEANUPWINDOW_H
