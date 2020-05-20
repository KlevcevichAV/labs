//
// Created by sanchir on 3.11.19.
//

#include "Economics.h"
#include <iostream>

namespace science{
    void Economics::outputInfo(){
        std::cout << name << "-" << definition << std::endl;
        std::cout << "К какой науке относятся :\n" << nameClass;
        std::cout << "Количесвто денег = " << amountOfMoney << std::endl;
    }

    Economics::Economics(){
        name = "Экономика";
        definition = "совокупность общественных наук, изучающих производство, распределение и потребление товаров и услуг.";
        double sumOfPriceAllGoods = 30;
        double sumOfCreditGoods = 30;
        double sumOfCreditPayments = 40;
        double MutualPayments = 10;
        double MoneyTraffic = 3;
        amountOfMoney = (sumOfPriceAllGoods - sumOfCreditGoods + sumOfCreditPayments - MutualPayments)/MoneyTraffic;
    }

}