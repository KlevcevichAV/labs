#ifndef INPUT1_H
#define INPUT1_H

#include <QDialog>

namespace Ui {
class Input1;
}

class Input1 : public QDialog
{
    Q_OBJECT

public:
    explicit Input1(QWidget *parent = nullptr);
    ~Input1();

private slots:
    void on_buttonBox_accepted();

    void on_pushButton_clicked();

private:
    Ui::Input1 *ui;
};

#endif // INPUT1_H
