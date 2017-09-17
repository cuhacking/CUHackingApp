# CUHacking Slack bot - CUSlacking!

CUSlacking is an awesome little Slack bot that allows announcements to be
pushed to the CUHacking app through the announcements channel.

It also allows you to match a mentor with a mentee through connecting with
the same app.

# Setup!

To set up CUSlacking, start by installing Ruby using your friendly
neighbourhood Mac package manager, `brew`, and installing one of Ruby's
more popular packaging tools `bundler`:

```bash
brew install ruby
gem install bundler
```

Then clone the repo (hopefully you've already done this):

```bash
git clone https://github.com/JackMc/COMP3004
```

Then you can install it by doing this:

```bash
cd COMP3004/slack_bot
bundle install
```

Then you need to set up the Slack token (enter the Slack token when asked):

```bash
./setup.sh
```

Then you can run the bot and develop on it:

```bash
lita start
```
