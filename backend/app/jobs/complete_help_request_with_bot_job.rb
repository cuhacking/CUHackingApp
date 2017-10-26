require "#{Rails.root}/lib/slack_party"

class CompleteHelpRequestWithBotJob < ApplicationJob
  queue_as :default

  def perform(help_request)
    user = help_request.user

    data_for_bot = {
      help_request: help_request.serializable_hash,
      user_id: user.id,
      user_name: user.name
    }

    response = SlackParty.post('/help_request/complete', body: data_for_bot.to_json, headers: {"Content-Type": "application/json"})
  end
end
