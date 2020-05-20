#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "QInputDialog"
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_pushButton_3_clicked()
{
    bool check;
    QString set1 = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = set1.toStdString();
    if(check){
        Multiset temp(t);
        set_1 = temp;
    }
    ui->outputSet1->setText(QString::fromStdString(set_1.getMultiset()));
}

//QString set2 = ui->inputSet2->text();
//std::string t = set2.toStdString();
//Multiset temp(t);
//set_2 = temp;
//QMessageBox::about(this , "" , "Мн-во введено");
//ui->output->setText("Первое мн-во :\n" + QString::fromStdString(set_1.getMultiset())+"\nВторое мн-во :\n"+QString::fromStdString(set_2.getMultiset()));

void MainWindow::on_pushButton_2_clicked()
{
    bool check;
    QString set2 = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = set2.toStdString();
    if(check){
        Multiset temp(t);
        set_2 = temp;
    }

   ui->outputSet2->setText(QString::fromStdString(set_2.getMultiset()));
}

void MainWindow::on_pushButton_clicked()
{
    ui->outputSet1->clear();
    ui->outputSet2->clear();
    set_1 = *new Multiset;
    set_2 = *new Multiset;
}

void MainWindow::on_Union_clicked()
{
    Multiset result = set_1 + set_2;
    QMessageBox::about(this , "Union" , QString::fromStdString(result.getMultiset()));
}

void MainWindow::on_Intersection_clicked()
{
    Multiset result = set_1 * set_2;
    QMessageBox::about(this , "Intersection" , QString::fromStdString(result.getMultiset()));
}

void MainWindow::on_Complement_clicked()
{
    Multiset result = set_1 - set_2;
    QMessageBox::about(this , "Intersection" , QString::fromStdString(result.getMultiset()));
}

void MainWindow::on_emptySet1_clicked()
{
    if(set_1.empty()) {
        QMessageBox::about(this , "" , "В множестве нет элементов!");
    }else {QMessageBox::about(this , "" , "В множестве есть элементы!");}
}

void MainWindow::on_emptySet2_clicked()
{
    if(set_2.empty()) {
        QMessageBox::about(this , "" , "В множестве нет элементов!");
    }else {QMessageBox::about(this , "" , "В множестве есть элементы!");}
}

void MainWindow::on_buleanSet1_clicked()
{
    QMessageBox::about(this , "Bulean" , QString::fromStdString(set_1.boolean()));
}

void MainWindow::on_buleanSet2_clicked()
{
    QMessageBox::about(this , "Bulean" , QString::fromStdString(set_2.boolean()));
}

void MainWindow::on_sizeSet1_clicked()
{
    QMessageBox::about(this , "Size" , QString::number(set_1.size()));
}

void MainWindow::on_sizeSet2_clicked()
{
    QMessageBox::about(this , "Size" , QString::number(set_2.size()));
}

void MainWindow::on_addElementTo1Set_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check)set_1.addElement(t);
    ui->outputSet1->setText(QString::fromStdString(set_1.getMultiset()));
}

void MainWindow::on_addElementTo2Set_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check)set_2.addElement(t);
    ui->outputSet2->setText(QString::fromStdString(set_2.getMultiset()));
}

void MainWindow::on_deleteElementTo1Set_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check)set_1.deleteElement(t);
    ui->outputSet1->setText(QString::fromStdString(set_1.getMultiset()));
}

void MainWindow::on_deleteElementTo1Set_2_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check)set_2.deleteElement(t);
    ui->outputSet2->setText(QString::fromStdString(set_2.getMultiset()));
}

void MainWindow::on_searchSet1_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check){
        if(set_1[t]){
            QMessageBox::about(this , "" , "Элемент есть во множестве!");
        }else QMessageBox::about(this , "" , "Элемента нет во множестве!");
    }
}

void MainWindow::on_searchSet2_clicked()
{
    bool check;
    QString str = QInputDialog::getText( 0, "Input", "Element:", QLineEdit::Normal, "", &check);
    std::string t = str.toStdString();
    if(check){
        if(set_2[t]){
            QMessageBox::about(this , "" , "Элемент есть во множестве!");
        }else QMessageBox::about(this , "" , "Элемента нет во множестве!");
    }
}
