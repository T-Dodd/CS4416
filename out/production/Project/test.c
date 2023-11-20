//*Key value pair assignment for CS 4461 Tyler Dodd*/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


/*Placeholder functions*/
void put();
void get();
void delete();
void clear();
void all();
void save();
void load();
void runtests();

const int MAX_STRING_SIZE = 50;
const int MAX_PAIRS = 100;
const int PAIR_SIZE = sizeof(int) + (sizeof(char) * MAX_STRING_SIZE);

/*Pair struct to represent a key value pair*/
typedef struct Pair
{
  int key;
  char value[50];
} Pair;


/*Pseudo dictionary data structure to store the key value pairs in local memory (maximum of 1,000 KV pairs)*/
typedef struct Dictionary
{
  Pair pairs[100];
} Dictionary;


/*Handles command line argument input*/
void
handleArgs(char *arguments, Dictionary **dictPtr)
{

    char delim[] = ",";
    char *token1 = strtok(arguments, delim);//getting the first argument (should be a single letter or 'test')
    char *token2 = strtok(NULL, delim);  //getting the second argument (should be a key or null)
    char *token3 = strtok(NULL, delim);  //getting the third argument (should be a string (value) or null)

    //printf("First token: %s\n", token1);
    //printf("Second token: %s\n", token2);
    //printf("Third token: %s\n", token3);

    if(strcmp(token1,"a") == 0 && token2 == NULL && token3 == NULL){
      printf("Fetching all entries\n");
      all(dictPtr);
    }
    else if(strcmp(token1,"c") == 0 && token2 == NULL && token3 == NULL){
      printf("Clearing all entries\n");
      clear(dictPtr);
    }
    else if(strcmp(token1,"d") == 0 && strcmp(token2,"\0") != 0 && token3 == NULL){
      printf("Attempting to delete pair with key %s\n", token2);
      delete(atoi(token2), dictPtr);
    }
    else if(strcmp(token1,"g") == 0 && strcmp(token2,"\0") != 0 && token3 == NULL){
      printf("Attempting to get pair with key %s\n", token2);
      get(atoi(token2),dictPtr);
    }
    else if(strcmp(token1,"p") == 0 && strcmp(token2,"\0") != 0 && strcmp(token3,"\0") != 0){
      printf("Creating new kv pair: %s,%s\n",token2,token3);
      put(atoi(token2),token3,dictPtr);
    }
    else if(strcmp(token1, "test") == 0)
    {
	printf("Running tests\n");
	runtests();
    }
    else
    {
      printf("Bad command detected: %s,%s,%s\n", token1, token2, token3);
    }
}

int
main(int argc, char *argv[])
{
  printf("Running Program %s\n", argv[0]);
  //char *fileContents = load();
  //const char fileName[] = "Database.txt";
  Dictionary *dict = calloc(MAX_PAIRS, sizeof(Pair));
  if(argc > 1)
  {
      printf("%d Command Line Arguments Detected\n", argc-1);
      for(int args = 0; args < argc -1; args++){ //iterating through all command line arguments
	printf("argument %d = %s\n", args+1, argv[args+1]);
	char *string = strdup(argv[args+1]); //copying the command line argument into string form
	handleArgs(string, &dict);
      }
  }
  free(dict);
}


void /*tests creation of structs with default values, and tests methods*/
runtests()
{
  Dictionary *testDictPtr = calloc(MAX_PAIRS, sizeof(Pair));
  Pair *testPairPtr = malloc(sizeof(Pair));
  Pair *pair2Ptr = malloc(sizeof(Pair));

  Pair testPair = *testPairPtr;
  testPair.key = 10;
  strcpy(testPair.value, "test");
  printf("Test Pair: %d,%s\n", testPair.key, testPair.value);

  Pair pair2 = *pair2Ptr;
  pair2.key = 1;
  strcpy(pair2.value, "test2");

  Dictionary testDict = *testDictPtr;
  memcpy(&testDict.pairs[0], &testPair, PAIR_SIZE);
  memcpy(&testDict.pairs[1], &pair2, PAIR_SIZE);
  printf("First dictionary entry: %d,%s\n", testDict.pairs[0].key, testDict.pairs[0].value);
  printf("Second dictionary entry: %d,%s\n",testDict.pairs[1].key, testDict.pairs[1].value);

  printf("Size of dictionary = %ld\n", sizeof(testDict));
  printf("Total number of dictionary indexes = %ld\n", (sizeof(testDict)/sizeof(Pair)));
  printf("Attempting to access dictionary entry 4: %d,%s\n", testDict.pairs[4].key, testDict.pairs[4].value);

  printf("Performing all command\n");
  all(testDictPtr);

  printf("Performing get command for key 1\n");
  get(1,&testDictPtr);

  put(1, "Carl", testDict);
  printf("Third dictionary entry: %d,%s\n",testDict.pairs[2].key, testDict.pairs[2].value);

  printf("Saving entries to database.txt\n");
  save(testDictPtr);

  free(testDictPtr);
  free(testPairPtr);
  free(pair2Ptr);
  testDictPtr = NULL;
  testPairPtr = NULL;
  pair2Ptr = NULL;
}

/*Determines the index of the next open location in the Dictionary if one exists, if none exist returns -1*/
int
getNextOpenSlot(Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  Dictionary d = *dict;
  for(int i = 0; i < MAX_PAIRS; i++){ //iterating through each index in the dictionary
    if(d.pairs[i].key == 0)
      {return i;} //checks to see if the address at location dict.pairs[i].key is 0 (empty index)
  }
  return -1;
}



/*Adds a key value pair to the data structure*/
void
put(int keyArg, char *valueArg, Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  Pair pair;
  pair.key = keyArg;
  strcpy(pair.value, valueArg);  //setting the pair.value array to be equal to the array passed as a parameter
  int index = getNextOpenSlot(&dict);
  if(index != -1)//ensuring that the dictionary has an empty index
  {
    memcpy(&dict->pairs[index], &pair, PAIR_SIZE);
  }
  else{printf("No available space in the dictionary or something went wrong\n");}
}


/*Gets a key associated with a specified value and prints the result*/
void
get(int key, Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  char value[MAX_STRING_SIZE];
  //iterating through an array of key values to see if the key exists
  int hasIndex = 0;
  for(int i = 0; i < MAX_PAIRS; i++){
    int key2 = dict->pairs[i].key;
    if(key == key2)
    {
      strcpy(value, dict->pairs[i].value);
      printf(" Located %d,%s\n",key,value);
      hasIndex = 1;
      break;
    }
  }
  if(hasIndex == 0){
    printf("Could not locate any pairs with the key %d\n", key);
  }
}


/*Deletes a key value pair from the data structure based on an input key*/
void
delete(int key, Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  int hasKey = 0;
  printf("Attempting to delete pair with key: %d\n",key);
  for(int i = 0; i < MAX_PAIRS; i++)//iterating through the pairs in the dictionary
  {
    int keyFound = dict->pairs[i].key;
    if(key == keyFound) //dictionary contained the key
      {
	dict->pairs[i].key = 0;
	for(int j = 0; j < MAX_STRING_SIZE; j++)//set each char in value string to NULL
	{
	  dict->pairs[i].value[j] = (char)0;
	}
	hasKey = 1;
	break; //break from the loop searching for a pair with the given key
      }
  }
  if(hasKey == 0){printf("No pair with key: %d\n", key);}//iterated all pairs and none had matching key
}

/*Clears all key value pairs from the data structure*/
void clear(Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  //set the database file to an empty string and set the data structure to an empty string
  printf("Attempting to delete all dictionary entries\n");
  for(int i = 0; i < MAX_PAIRS; i++) //iterating through each index in the dictionary
  {
    for(int j = 0; j < MAX_STRING_SIZE; j++)//iterating through each character in the value string and setting it to NULL
    {
      memcpy(dict->pairs[i].value, "\0", 1);
    }
    memcpy(&dict->pairs[i].key, "\0", sizeof(int));
  }
}

/*Prints all key value pairs to the screen*/
void
all(Dictionary* dict)
{
  printf("Printing all variables held within the dictionary\n------------------------------------------------------\n");
  char value[MAX_STRING_SIZE];
  for(int i = 0; i < MAX_PAIRS; i++)
  {
      int key = dict->pairs[i].key;
      if(strcmp(dict->pairs[i].value, "") != 0)
      {
	strcpy(value, dict->pairs[i].value);
	printf("%d,%s\n",key,value);
      }
  }
  printf("------------------------------------------------------\n");
}

/*Loads the contents of the database file into the data structure*/
void
load(Dictionary **dict)
{

}

/*Saves the contents of the data structure to the database file*/
void
save(Dictionary **dictPtr)
{
  Dictionary *dict = *dictPtr;
  FILE *file;
  file = fopen("database.txt", "w+");
  int key;
  for(int i = 0; i < MAX_PAIRS; i++)
  {
    key = dict->pairs[i].key;
      if(key != 0 && strcmp(dict->pairs[i].value, "") != 0)
	{
	  fprintf(file, "%d,%s\n", key, dict->pairs[i].value);
	}
  }
  fclose(file);
}