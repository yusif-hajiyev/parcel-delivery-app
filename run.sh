MS_USER="${PWD}/ms-user"
MS_AUTH="${PWD}/ms-auth"
MS_PARCEL="${PWD}/ms-parcel"
MS_API_GATEWAY="${PWD}/ms-api-gateway"

docker network create guavapay

echo "building images"
docker-compose -f $MS_AUTH/docker-compose.yml build & 
docker-compose -f $MS_USER/docker-compose.yml build &
docker-compose -f $MS_PARCEL/docker-compose.yml build &
docker-compose -f $MS_API_GATEWAY/docker-compose.yml build
echo " builded images"

echo "starting services"
docker-compose -f $MS_AUTH/docker-compose.yml up &
docker-compose -f $MS_USER/docker-compose.yml up &
docker-compose -f $MS_PARCEL/docker-compose.yml up &
docker-compose -f $MS_API_GATEWAY/docker-compose.yml up