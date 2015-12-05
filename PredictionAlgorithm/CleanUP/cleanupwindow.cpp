#include "cleanupwindow.h"
#include "ui_cleanupwindow.h"

CleanUpWindow::CleanUpWindow(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::CleanUpWindow)
{
    ui->setupUi(this);

    ui->addKeyToRemoveButton->setEnabled(false);
}

CleanUpWindow::~CleanUpWindow()
{
    delete ui;
}

void CleanUpWindow::on_keyLineEdit_textChanged(const QString &arg1)
{
    ui->addKeyToRemoveButton->setEnabled(!arg1.isEmpty());
}

void CleanUpWindow::on_addKeyToRemoveButton_clicked()
{
    if(keys.contains(ui->keyLineEdit->text()))
        return;

    keys.append(ui->keyLineEdit->text());
    ui->itemsToKeep->addItem(ui->keyLineEdit->text());
    ui->keyLineEdit->clear();
}

void CleanUpWindow::on_acceptButton_clicked()
{
    emit removeWords(keys);
    this->close();
}

void CleanUpWindow::on_cancelButton_clicked()
{
    this->close();
}
