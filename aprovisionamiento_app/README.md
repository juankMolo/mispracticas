# To Run application

## Start and SSH into Vagrant VM 

```
vagrant up
vagrant ssh
```

## Add the current user to the Docker group, then log out and back in to apply the changes

```
sudo usermod -aG docker $USER
exit 
vagrant ssh
```

## Start Microservices 

```
docker compose up
```
# To Stop application

## Stop Microservices 

```
Ctrl + C
```