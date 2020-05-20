//
// Created by sanchir on 13.12.19.
//

#include "Version.h"

void Version::setVersion(std::string version){
    this->version = version.substr(2 , version.length() - 3);
}