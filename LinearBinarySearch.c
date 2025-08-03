#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct student{
    int prn;
    char sname[50];
    char class[10];
    char mnum[10];
    int marks;
};

int binarySearchByPRN(struct student arr[], int n, int target) {
    int left = 0, right = n - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid].prn == target) return mid;
        else if (arr[mid].prn < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1; 
}

int linearSearchByName(struct student arr[],int n, char *target){
    int i=0;
    for(i=0;i<n;i++){
        if(strstr(arr[i].sname, target)){
            return i;
        }
        
    }
    return -1;
    
}
    


int main(){
    int x;
    struct student students[100];
    char line[1000];
    int count=0;
    FILE *fp;
 
    
    fp=fopen("cprog.txt", "r");
 

   if(!fp){
        printf("file cant be opened");
        return 1;
    }

    
    
    fgets(line, sizeof(line), fp);

    while(fgets(line,sizeof(line),fp))
    {
        
        line[strcspn(line, "\n")]=0;
        
        char *token= strtok(line, "\t");
        students[count].prn=atoi(token);
        
        token= strtok(NULL, "\t");
        strcpy(students[count].sname,token);
        
        token= strtok(NULL, "\t");
        strcpy(students[count].class,token);
        
        token= strtok(NULL, "\t");
        strcpy(students[count].mnum,token);
        
        token= strtok(NULL, "\t");
        students[count].marks=atoi(token);
        
        count++;
    }
    fclose(fp);
    
    
    int choice;
    while(choice!=3){
    printf("enter your choice :\n");
    printf("1.search by PRN: \n");
    printf("2. search by name: \n");
    printf("3. exit \n");
    scanf("%d", &choice);
    switch (choice){
        case 1:{
            int key;
            printf("enter the prn to search for: ");
            scanf("%d", &key);
            int index= binarySearchByPRN(students,count,key);
            if(index !=-1){
                printf("PRN: %d \n", students[index].prn);
                printf("Name: %s\n", students[index].sname);
                printf("Class: %s\n", students[index].class);
                printf("Mobile: %s\n", students[index].mnum);
                printf("Marks: %d\n", students[index].marks);
            }
            else{
                printf("student record not found");
            }
            break;
            
        }
        case 2:{
            char nem[50];
            printf("enter the name to search for");
            scanf("%s",nem);
            int index= linearSearchByName(students,count,nem);
            if(index !=-1){
                printf("PRN: %d \n", students[index].prn);
                printf("Name: %s\n", students[index].sname);
                printf("Class: %s\n", students[index].class);
                printf("Mobile: %s\n", students[index].mnum);
                printf("Marks: %d\n", students[index].marks);
            }
            else{
                printf("student record not found");
            }
            break;
        }
        case 3:{
            break;
        }
        
        
    }
    }
    return 0;
}


