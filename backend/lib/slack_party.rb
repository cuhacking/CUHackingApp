class SlackParty
  include HTTParty
  base_uri Rails.application.config.slack_bot_base
end
