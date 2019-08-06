# Simple Index Search App  
  
## Code language  
Java 11  
  
## Creating a distribution  

```
./gradlew clean build
```


  
## Running the application  
To run the application you need to create a distribution. 
```
./gradlew clean build
```
Once build has passed `search-1.0-SNAPSHOT.zip` file will be created under `build/distributions`. 
Unzip the distribution file. From the root directory of the unzip result run:   
  
```
./bin/search
```

## Usage
### show-fields
List searchable fields

```
    NAME
    	show-fields - Display searchable fields for searchable entities
    
    SYNOPSYS
    	show-fields
```

Eg.
```
shell:>show-fields

ORGANIZATION
--------------------------------------------------
    _id
    url
    external_id
    name
    domain_names
    created_at
.
.
.

```

### search
Performs a full word case-sensitive match on field values of entities. 
Entity name can be Organization, User or Ticket (case insensitive).
The available fields for entities can be discovered by running `show-fields`.
To search for empty values leave omit the search value.

```
    NAME
	search - Full word match on field values of Organizations, Users or Tickets

SYNOPSYS
	search [--entity] string  [--field] string  [[--word] string]  

OPTIONS
	--entity  string
		Entity name. It can be [Organization | User | Ticket].
		[Mandatory]

	--field  string
		Field to search. For field info see show-fields command.
		[Mandatory]

	--word  string
		Value to search (full work match only). Omit it for empty value search.
		[Optional, default = ]
```

Eg.
```
shell:>search organization details MegaCorp

Organization Details
--------------------------------------------------

Organization
--------------------------------------------------
id: 121
url: http://initech.zendesk.com/api/v2/organizations/121.json
externalId: 3fffbf20-9172-4d1d-923b-f247d9132e3a
name: Hotcâkes
.
.
.
```

## Help
For "entire" app help
```
shell:>help

AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Search Command
        search: Full word match on field values of Organizations, Users or Tickets

Show Fields Command
        show-fields: Display searchable fields for searchable entities
```

For specific command help
``` 
shell:> help <command>
```
## Stress test
In order to ensure the evaluation criteria 4 (_Performance - should gracefully handle a significant increase in amount of data provided (e.g 10000+ users)_) is covered the test
`SearchAppPerformanceTest` runs an integration test using  a data source with 100K users.
Indexing and 100,000 search commands have run under 35 seconds.  

## Assumptions and Trade-offs

 - All entity ids and complex types (Date, Timezone, Locale) have been taken as `Strings` to simplify the implementation
 - It is not required to search for multiple words at the same time.
 - Search for values is case sensitive
 - Search for spaces is not supported
 - Data does not change. Once data has been indexed the source data will not change.
 - Null values and empty strings are indexed the same way


  