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

Secrets = JSON.parse(
  File.read('config/secrets.json'),
  symbolize_names: true, object_class: SecretHash
)
