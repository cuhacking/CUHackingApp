# frozen_string_literal: true

class SecretHash < Hash
  class SecretNotFound < StandardError; end

  def initialize
    super do |_secrets, key|
      raise SecretNotFound, "Secret `#{key}` not found"
    end
  end

  def to_h
    each_with_object({}) do |h, (k, v)|
      h[k] = v
    end
  end
end

class EnvironmentsSecrets
  def [](param)
    env_name = param.to_s.upcase
    ENV[env_name]
  end
end

if Rails.env.development?
  Secrets = JSON.parse(
    File.read('config/secrets.json'),
    symbolize_names: true, object_class: SecretHash
  )
else
  Secrets = EnvironmentsSecrets.new
end
