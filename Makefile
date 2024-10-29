USER_ID = $(shell id -u)
GROUP_ID = $(shell id -g)

export UID = $(USER_ID)
export GID = $(GROUP_ID)

help: ## Display available commands
	$(info usage: make [command] [<service_name>])
	$(info possible commands:)
	@grep -F -h "##" $(MAKEFILE_LIST) | grep -F -v fgrep | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

down: ## Down container(s)
	docker compose down --remove-orphans $(filter-out $@,$(MAKECMDGOALS))

logs: ## Tail logs
	docker compose logs -tf --tail="500" $(filter-out $@,$(MAKECMDGOALS))

start: ## start the service
	docker compose up --build -d --no-recreate $(filter-out $@,$(MAKECMDGOALS))
	make logs $(filter-out $@,$(MAKECMDGOALS))

stop: ## stop the service
	docker compose rm -f -s -v $(filter-out $@,$(MAKECMDGOALS))

restart: ## start the service
	make stop $(filter-out $@,$(MAKECMDGOALS))
	make start $(filter-out $@,$(MAKECMDGOALS))
	docker compose logs -tf --tail="500" $(filter-out $@,$(MAKECMDGOALS))

connect: ## Connect on a remote terminal
	docker compose exec $(filter-out $@,$(MAKECMDGOALS)) bash

pull: ## Pull image(s) of the service(s)
	docker compose pull $(filter-out $@,$(MAKECMDGOALS))

ls: ## list services in current stack
	docker compose config --services

ps: ## list status services in current stack
	docker compose ps

reload: ## stop/remove/pull/start container
	make stop $(filter-out $@,$(MAKECMDGOALS))
	make pull $(filter-out $@,$(MAKECMDGOALS))
	make start $(filter-out $@,$(MAKECMDGOALS))

package: ## build application
	./mvnw clean package