#include "input1.h"
#include "ui_input1.h"

Input1::Input1(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Input1)
{
    ui->setupUi(this);
}

Input1::~Input1()
{
    delete ui;
}

void Input1::on_pushButton_clicked()
{
    QString set1 = ui->set11->text();
}
