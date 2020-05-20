#include "input.h"
#include "ui_input.h"

input::input(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::input)
{
    ui->setupUi(this);
}

input::~input()
{
    delete ui;
}

void input::on_pushButton_clicked()
{
    QString result = ui->lineEdit->text();

}
