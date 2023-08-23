FROM ubuntu:20.04

RUN apt-get update
RUN apt-get install -y vim
RUN apt-get install -y net-tools
RUN apt-get install -y nginx
RUN apt-get install -y git
RUN apt-get install -y wget
RUN apt-get install -y dpkg
RUN apt-get install -y curl

RUN apt-get update
RUN apt install -y libc6-i386 libc6-x32 libxi6 libxtst6 libxrender1
RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb && dpkg -i jdk-17_linux-x64_bin.deb
ENV JAVA_HOME=/usr/lib/jvm/jdk-17/
ENV PATH=$PATH:$JAVA_HOME/bin

RUN apt-get update
RUN apt-get install -y gnupg
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list
RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list
RUN curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add
RUN apt-get update
RUN apt-get install sbt

COPY ./dummy.txt .