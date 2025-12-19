# This is an open lab to anyone.
## Currently the lab contains
| Feature       |   Stack       | Status |
|:-------------:|:-------------:|:-------------:|
| Auth Server | Springboot(Security, Jpa, Web), Kotlin, Postgres| Testable ([openapi-spec.yaml](https://github.com/Pseudonotes/labs/blob/master/auth-server/open-api-spec.yaml)) |


<hr>

To use project locally:
  1. Clone project files
     ```bash
         git clone https://github.com/Pseudonotes/labs.git
      ```
  2. Open and edit docker compose file as per your local settings.
  3. Run docker containers
     ```bash
       docker compose up -d
     ```
      <em>NOTE: Ensure all containers are created and running.</em>
      ```bash
        docker container ls
      ```
  4. Open a specific project feauture as listed above from an IDE, Inspect, and run.
  5. Contribute via PR 💙
  6. Tear down resources after testing
     ```bash
       docker compose down -v
     ```
     
# Keep Building.
