//-----------------------------------------------//
//  Esercizio Sistemi distribuiti 14/10/2019     //
//              Server_D.c                       //
//           Nicolas Nucifora                    //
//-----------------------------------------------//

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

#define LOCALPORT 9999
#define MAXBUFF 1024

char* titoli[5] = {"Trono di spade", "Il signore degli anelli", "Il guardiano degli innocenti", "Le cronache di narnia", "Hunger games"};
char* testi[5] = {"Uccidi il ragazzo dentro di te jon snow", "Un anello per domarli", "Geralt di rivia", "L'armadio porta a narnia", "La ragazza di fuoco"};

char* inizioFine(char*);

int main(int argc, char** argv){

    int welcomeSock, connectionSock, retcode, used = 1;
    struct sockaddr_in server, client;
    socklen_t clientLen;
    char msg[MAXBUFF];

    if((welcomeSock = socket(AF_INET, SOCK_STREAM, 0)) < 0){
        perror("socket()\n");
        exit(-1);
    }

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(LOCALPORT);

    if(setsockopt(welcomeSock, SOL_SOCKET, SO_REUSEADDR, &used, sizeof(int)) < 0){
        perror("setsockopt()\n");
        exit(-1);
    }
        

    if((bind(welcomeSock, (struct sockaddr *)&server, sizeof(server))) < 0){
        perror("bind()\n");
        exit(-1);
    }

    printf("...Server is listening...\n");

    listen(welcomeSock, 2);

    clientLen = sizeof(client);

    while(1){
        if((connectionSock = accept(welcomeSock, (struct sockaddr *)&client, &clientLen)) < 0){
            perror("accept()\n");
            exit(-1);
        }

        printf("Connection established with client(%s) on socket %d\n", inet_ntoa(client.sin_addr), LOCALPORT);

        while((retcode = read(connectionSock, msg, MAXBUFF-1)) > 0){
            msg[retcode] = '\0';
            printf("Message from %s: %s\n\n", inet_ntoa(client.sin_addr), msg);
            char* s = inizioFine(msg);
            if((retcode = write(connectionSock, s, strlen(s))) < 0){
                perror("write()\n");
                exit(-1);
            }
            printf("---Sent %d bytes---\n", retcode);
        }
        if(retcode == 0){
            printf("Client closed the connection\n");
        }
        else{
            perror("retcode < 0\n");
            exit(-1);
        }
        close(connectionSock);
    }
 }

/*char* inizioFine(char* titolo){  //provare strcmp con il client invece di telnet
    for(int i = 0; i < 5; i++){
        int check = 1;
        for(int j = 0; j < strlen(titoli[i]); j++)
            if(titolo[j] != titoli[i][j])
                check = 0;
        if(check)
            return testi[i];
    }
    return "Not Found!\n";
}*/

char* inizioFine(char* titolo){  //provare strcmp con il client invece di telnet
    for(int i = 0; i < 5; i++){
        if(strncmp(titolo, titoli[i], strlen(titoli[i])) == 0)
            return testi[i];
    }
    return "Not Found!\n";
}