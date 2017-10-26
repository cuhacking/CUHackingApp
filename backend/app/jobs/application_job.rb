class ApplicationJob < ActiveJob::Base
    rescue_from(StandardError) do |exception|
      retry_job wait: 1.minutes
    end
end
