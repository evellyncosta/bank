THIS_FILE := $(lastword $(MAKEFILE_LIST))
.PHONY: help build up start down destroy stop restart logs ps db-shell
help:
	make -pRrq  -f $(THIS_FILE) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$'
up:
	docker-compose -f docker-compose.yaml up -d $(c)
down:
	docker-compose -f docker-compose.yaml down $(c)
destroy:
	docker-compose -f docker-compose.yaml down -v $(c)
stop:
	docker-compose -f docker-compose.yaml stop $(c)
restart:
	docker-compose -f docker-compose.yaml stop $(c)
	docker-compose -f docker-compose.yaml up -d $(c)
logs:
	docker-compose -f docker-compose.yaml logs --tail=100 -f $(c)
ps:
	docker-compose -f docker-compose.yaml ps
db-shell:
	docker-compose -f docker-compose.yaml exec db psql -Upostgres