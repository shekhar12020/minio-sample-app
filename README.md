Install minIO
sudo apt update
wget https://dl.min.io/server/minio/release/linux-amd64/minio
chmod +x minio
./minio server /home/jyotrimaya/work_jyotrimaya/dev/data

Access Minio console  and create bucket i.e gulfnet
http://127.0.0.1:39153/buckets

Run application and test apis
For Download 

curl --location 'http://localhost:9091/files/download?bucketName=gulfnet&objectName=thik.png

For Upload 

curl --location 'http://localhost:9091/files/upload' \
--form 'file=@"/home/jyotrimaya/Pictures/thik.png"' \
--form 'bucketName="gulfnet"'
