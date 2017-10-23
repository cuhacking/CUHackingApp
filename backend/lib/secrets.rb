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

# Loads secrets into Secret constant.
#
# `./config/secrets.json` can always be assumed to hold the appropriate
# decrypted secrets for the environment. It's up to the deploy method to ensure
# that is the case.
#
# We load eagerly instead of lazily to ensure exceptions such as `ENOENT`,
# `EACCESS` or JSON parse errors are raised on boot rather than at runtime where
# it could 500 requests. Instead, exceptions on boot will perform a rollback.
Secrets = JSON.parse(
  File.read('config/secrets.json'),
  symbolize_names: true, object_class: SecretHash
)
