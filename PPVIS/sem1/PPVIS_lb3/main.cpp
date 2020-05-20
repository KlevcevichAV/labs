#include <iostream>
#include "XMLDocument.h"


using namespace std;

int main() {
    string s = "/bookstore/book/title[attr(\"lang\", \"en\") || !text(\"Гарри Поттер\") || text(\"Изучаем XML\")]";
//    string s = "/";
    cout << "Command : " << s << endl;
//    Command command;
    Tag temp;
//    command.splitCommand(s);
//    command.output();
    XMLDocument f("/home/sanchir/CLionProjects/PPVIS_lb3/xml-example.xml");
    f.outputTree();
    cout << "\n\n\n\n\n\n\n";
    temp = f.command(s);
    temp.output();
    return 0;
}