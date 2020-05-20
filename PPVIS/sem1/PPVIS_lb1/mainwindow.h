#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QLabel>
#include "/home/sanchir/CLionProjects/task/PPVIS_lb1/Multiset.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_pushButton_3_clicked();

    void on_pushButton_2_clicked();

    void on_pushButton_clicked();

    void on_Union_clicked();

    void on_Intersection_clicked();

    void on_Complement_clicked();

    void on_emptySet1_clicked();

    void on_emptySet2_clicked();

    void on_buleanSet1_clicked();

    void on_buleanSet2_clicked();

    void on_sizeSet1_clicked();

    void on_sizeSet2_clicked();

    void on_addElementTo1Set_clicked();

    void on_addElementTo2Set_clicked();

    void on_deleteElementTo1Set_clicked();

    void on_deleteElementTo1Set_2_clicked();

    void on_searchSet1_clicked();

    void on_searchSet2_clicked();

private:
    Ui::MainWindow *ui;
    Multiset set_1 , set_2;

};
#endif // MAINWINDOW_H
