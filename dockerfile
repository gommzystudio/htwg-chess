FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1

RUN apt-get update --allow-releaseinfo-change && apt-get install -y \
    xorg \
    openbox \
    libxext-dev \
    libxrender-dev \
    libxtst-dev \
    libxi-dev \
    libgl1-mesa-glx \
    libgl1-mesa-dri


RUN wget https://download2.gluonhq.com/openjfx/21/openjfx-21_linux-aarch64_bin-sdk.zip \
    && unzip openjfx-21_linux-aarch64_bin-sdk.zip -d /opt/javafx \
    && rm openjfx-21_linux-aarch64_bin-sdk.zip

WORKDIR /app

ADD . /app

RUN sbt compile

CMD ["sbt", "run"]
